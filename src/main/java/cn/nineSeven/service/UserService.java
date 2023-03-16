package cn.nineSeven.service;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.dto.RegisterUserDto;
import cn.nineSeven.entity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 15:56:54
 */
public interface UserService extends IService<User> {

    Result login(LoginUserDto loginUserDto);

    Result register(RegisterUserDto registerUserDto, HttpServletRequest request);

    Result sendCode(String email, HttpSession session);

    Result logout();

    Result getUserInfoById(Long id);
}

