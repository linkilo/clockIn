package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clock")
public class CLockController {

    @Autowired
    ClockService clockService;
    @GetMapping("/list")
    public Result listAllClock(Integer grade, Integer pageNum, Integer pageSize){
        return clockService.listAllClock(grade, pageNum, pageSize);
    }
    @PostMapping("/{id}")
    public Result clock(@PathVariable("id") Long id){
        return clockService.clock(id);
    }
}
