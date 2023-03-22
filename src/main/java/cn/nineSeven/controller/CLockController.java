package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clock")
public class CLockController {

    @Autowired
    ClockService clockService;

    @GetMapping("/list")
    public Result listAllClock(Integer grade, Integer pageNum, Integer pageSize) {
        return clockService.listAllClock(grade, pageNum, pageSize);
    }
    @PostMapping("/{id}")
    public Result clock(@PathVariable("id") Long id){
        return clockService.clock(id);
    }

    @GetMapping("/{id}")
    public Result getClockById(@PathVariable("id") Long id) {
        return clockService.getClockById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("@ps.hasPermission('clock:update:duration')")
    public Result updateDuration(@PathVariable("id") Long id, Integer duration){
        return clockService.updateDuration(id, duration);
    }
}
