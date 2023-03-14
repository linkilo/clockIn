package cn.nineSeven.service;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.dto.LoginUserDto;
import cn.nineSeven.entity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 15:56:54
 */
public interface UserService extends IService<User> {

    Result login(LoginUserDto loginUserDto);
}

