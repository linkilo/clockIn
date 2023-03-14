package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }
}
