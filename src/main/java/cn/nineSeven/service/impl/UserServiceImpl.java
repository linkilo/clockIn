package cn.nineSeven.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.dto.RegisterUserDto;
import cn.nineSeven.entity.dto.UpdateUserDto;
import cn.nineSeven.entity.pojo.Clock;
import cn.nineSeven.entity.pojo.LoginUser;
import cn.nineSeven.entity.pojo.User;
import cn.nineSeven.entity.vo.LoginUserVo;
import cn.nineSeven.entity.vo.UserInfoVo;
import cn.nineSeven.mapper.UserMapper;
import cn.nineSeven.service.ClockHistoryService;
import cn.nineSeven.service.ClockService;
import cn.nineSeven.service.UserService;
import cn.nineSeven.utils.BeanCopyUtils;
import cn.nineSeven.utils.JWTUtils;
import cn.nineSeven.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 15:56:55
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClockService clockService;

    @Autowired
    private OssUploadService ossUploadService;


    @Override
    public Result login(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),loginUserDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            return Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        User user = loginUser.getUser();
        redisTemplate.opsForValue().set(SystemConstant.REDIS_LOGIN_USER + user.getId(), loginUser, 60 * 24, TimeUnit.MINUTES);

        String token = JWTUtils.createJWT(user.getId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        LoginUserVo loginUserVo = new LoginUserVo(token, userInfoVo);

        return Result.okResult(loginUserVo);
    }

    @Override
    public Result register(RegisterUserDto registerUserDto, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("registerCode");
        if(!code.equals(registerUserDto.getCode())){
            return Result.errorResult(AppHttpCodeEnum.CODE_FALSE);
        }
        String userName = registerUserDto.getUsername();
        if(StrUtil.isBlank(userName)) {
            return Result.errorResult(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User one = lambdaQuery().eq(User::getUsername, userName).one();
        if(one != null) {
            return Result.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);

        save(user);
        Clock clock = new Clock(LocalDateTime.now(), SystemConstant.CLOCKED_STATUS, 0, SystemConstant.FIRST_GRADE_CLOCK_TARGET);
        if(user.getGrade() == 2) {
            clock.setTargetDuration(SystemConstant.SECOND_GRADE_CLOCK_TARGET);
        }
        clockService.save(clock);
        return Result.okResult();
    }

    @Override
    public Result sendCode(String email, HttpSession session) {
        SimpleMailMessage smm = new SimpleMailMessage();		//创建邮件对象
        try {
            smm.setSubject("注册验证码");	//设置邮件主题
            String code = String.valueOf(RandomUtil.randomInt(1000, 9999)) ;
            session.setAttribute("registerCode", code);
            smm.setText("您的验证码为：" + code);		//编辑邮件内容
            smm.setTo(email);	//设置邮件发送地址
            smm.setFrom(from);	//设置邮件发送源址
            mailSender.send(smm); //发送邮件
            return Result.okResult();
        }catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.CODE_SEND_ERROR);
        }
    }

    @Override
    public Result logout() {
        Long id = SecurityUtils.getUserId();
        Boolean flag = redisTemplate.delete(SystemConstant.REDIS_LOGIN_USER);
        if(BooleanUtil.isTrue(flag)) {
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getUserInfoById(Long id) {
        User user = getById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return Result.okResult(userInfoVo);
    }

    @Override
    public Result updateUserInfo(UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        boolean flag = updateById(user);
        if(!flag) return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        return Result.okResult();
    }

    @Override
    public Result uploadAva(MultipartFile file) {
        return ossUploadService.uploadImg(file);
    }
}

