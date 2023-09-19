package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.annotation.SystemLog;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.dto.RegisterUserDto;
import cn.nineSeven.entity.dto.UpdateUserDto;
import cn.nineSeven.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录")
    @SystemLog(businessName = "登录")
    public Result login(LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    public Result register(@RequestBody RegisterUserDto registerUserDto, HttpServletRequest request) {
        return userService.register(registerUserDto, request);
    }

    @PostMapping("/register/sendCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
    })
    @SystemLog(businessName = "发验证码")
    public Result sendCode(String username, HttpSession session) {
        return userService.sendCode(username, session);
    }

    @PostMapping("/logout")
    public Result logout(){
        return userService.logout();
    }

    @GetMapping("/info/{id}")
    @SystemLog(businessName = "获得个人信息")
    public Result getUserInfoById(@PathVariable("id") Long id){
        return userService.getUserInfoById(id);
    }

    @PutMapping("/info/update")
    @SystemLog(businessName = "更新用户信息")
    public Result updateUserInfo(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUserInfo(updateUserDto);
    }

    @PostMapping("/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "MultipartFile"),
    })
    public Result uploadAva(MultipartFile file){
        return userService.uploadAva(file);
    }
}
