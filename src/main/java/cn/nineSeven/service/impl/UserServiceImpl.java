package cn.nineSeven.service.impl;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.pojo.LoginUser;
import cn.nineSeven.entity.pojo.User;
import cn.nineSeven.entity.vo.LoginUserVo;
import cn.nineSeven.entity.vo.UserInfoVo;
import cn.nineSeven.mapper.UserMapper;
import cn.nineSeven.service.UserService;
import cn.nineSeven.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    @Override
    public Result login(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUserName(),loginUserDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            return Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        User user = loginUser.getUser();
        redisTemplate.opsForValue().set(SystemConstant.redisLoginUser + user.getId(), loginUser, 60 * 24, TimeUnit.MINUTES);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());

        String token = JWTUtil.createToken(map, SystemConstant.JWTKey.getBytes());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        LoginUserVo loginUserVo = new LoginUserVo(token, userInfoVo);

        return Result.okResult(loginUserVo);
    }
}

