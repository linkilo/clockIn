package cn.nineSeven.service.impl;

import org.springframework.stereotype.Service;
import cn.nineSeven.utils.SecurityUtils;

import java.util.List;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission){
        Long userId = SecurityUtils.getUserId();

        if(userId == 1L){
            return true;
        }

        List<String> permissions = SecurityUtils.getLoginUser().getPerms();
        return permissions.contains(permission);
    }

}
