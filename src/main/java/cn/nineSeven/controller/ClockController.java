package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.annotation.SystemLog;
import cn.nineSeven.service.ClockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clock")
@Api(tags = "打卡相关接口")
public class ClockController {

    @Autowired
    ClockService clockService;

    @GetMapping("/list")
    @SystemLog(businessName = "打卡信息")
    public Result listAllClock(Integer grade, Integer pageNum, Integer pageSize) {
        return clockService.listAllClock(grade, pageNum, pageSize);
    }
    @PostMapping("/{id}")
    @SystemLog(businessName = "上下卡")
    public Result clock(@PathVariable("id") Long id){
        return clockService.clock(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取个人打卡信息")
    public Result getClockById(@PathVariable("id") Long id) {
        return clockService.getClockById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("@ps.hasPermission('clock:update:duration')")
    public Result updateDuration(@PathVariable("id") Long id, Integer duration){
        return clockService.updateDuration(id, duration);
    }
}
