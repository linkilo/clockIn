package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    @GetMapping
    public Result getAllPermsById(@PathVariable("id") Long id) {
        return menuService.getAllPermsById(id);
    }
}
