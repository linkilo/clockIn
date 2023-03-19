package cn.nineSeven.controller;

import cn.nineSeven.entity.Result;
import cn.nineSeven.service.ClockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clockHistory")
public class ClockHistoryController {
    @Autowired
    ClockHistoryService clockHistoryService;
    @GetMapping("/list")
    public Result list(Integer week, Integer grade, Integer pageNum, Integer pageSize){
        return clockHistoryService.list(week, grade, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public Result getClockHistoryById(@PathVariable("id") Long id, Integer pageNum, Integer pageSize) {

        return clockHistoryService.getClockHistoryById(id, pageNum, pageSize);
    }
}
