package cn.nineSeven.service;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 18:48:47
 */
public interface MenuService extends IService<Menu> {

    Result getAllPermsById(Long id);
}

