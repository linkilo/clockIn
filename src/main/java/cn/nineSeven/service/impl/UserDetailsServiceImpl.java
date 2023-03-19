package cn.nineSeven.service.impl;

import cn.nineSeven.entity.pojo.LoginUser;
import cn.nineSeven.entity.pojo.Menu;
import cn.nineSeven.entity.pojo.User;
import cn.nineSeven.entity.vo.MenuInfoVo;
import cn.nineSeven.mapper.MenuMapper;
import cn.nineSeven.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        User user = userMapper.selectOne(lqw);
        if(user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        List<Menu> menus = menuMapper.selectMenusByUserId(user.getId());

        List<String> perms = menus.stream()
                .map(menu -> menu.getPerms())
                .collect(Collectors.toList());
        return new LoginUser(user, perms);
    }
}
