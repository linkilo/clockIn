package cn.nineSeven.service.impl;

import cn.nineSeven.entity.pojo.Menu;
import cn.nineSeven.mapper.MenuMapper;
import cn.nineSeven.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 18:48:47
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}

