package cn.nineSeven.service.impl;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.Menu;
import cn.nineSeven.entity.vo.MenuInfoVo;
import cn.nineSeven.mapper.MenuMapper;
import cn.nineSeven.service.MenuService;
import cn.nineSeven.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 18:48:47
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Result getAllPermsById(Long id) {
        MenuMapper mapper = getBaseMapper();
        List<Menu> menus = null;
        if(id == 1L){
            menus = list();
        }else{
            menus = mapper.selectMenusByUserId(id);
        }
        List<MenuInfoVo> menuInfoVos = BeanCopyUtils.copyBeanList(menus, MenuInfoVo.class);
        return Result.okResult(menuInfoVos);
    }
}

