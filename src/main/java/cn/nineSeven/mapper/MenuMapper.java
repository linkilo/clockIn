package cn.nineSeven.mapper;

import cn.nineSeven.entity.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 18:48:47
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select perms from role\n" +
            "    left join role_menu rm on role.id = rm.role_id\n" +
            "    left join user_role ur on role.id = ur.role_id\n" +
            "    left join menu m on rm.menu_id = m.id\n" +
            "    where ur.user_id = #{id}")
    List<String> getPermsByUserId(Long id);
}

