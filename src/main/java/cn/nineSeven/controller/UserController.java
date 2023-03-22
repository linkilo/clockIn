package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.dto.RegisterUserDto;
import cn.nineSeven.entity.dto.UpdateUserDto;
import cn.nineSeven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserDto registerUserDto, HttpServletRequest request) {
        return userService.register(registerUserDto, request);
    }

    @PostMapping("/register/sendCode")
    public Result sendCode(String username, HttpSession session) {
        return userService.sendCode(username, session);
    }

    @PostMapping("/logout")
    public Result logout(){
        return userService.logout();
    }

    @GetMapping("/info/{id}")
    public Result getUserInfoById(@PathVariable("id") Long id){
        return userService.getUserInfoById(id);
    }

    @PutMapping("/info/update")
    public Result updateUserInfo(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUserInfo(updateUserDto);
    }

    @PostMapping("/upload")
    public Result uploadAva(MultipartFile file){
        return userService.uploadAva(file);
    }
}
