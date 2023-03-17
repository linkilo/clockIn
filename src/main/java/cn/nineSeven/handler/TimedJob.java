package cn.nineSeven.handler;

import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.pojo.ClockHistory;
import cn.nineSeven.service.ClockHistoryService;
import cn.nineSeven.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimedJob {

    @Autowired
    ClockService clockService;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ClockHistoryService clockHistoryService;


    @Scheduled(cron = "0 0 0 ? * MON")
    public void cleanDuration(){
        Integer week = (Integer) redisTemplate.opsForValue().get(SystemConstant.REDIS_WEEK);
        List<ClockHistory> clockHistories = clockService.list().stream()
                .map(clock -> new ClockHistory(clock.getId(), week, clock.getTotalDuration(), clock.getTotalDuration() >= clock.getTotalDuration() ? 1 : 0))
                .collect(Collectors.toList());

        clockHistoryService.saveBatch(clockHistories);

        redisTemplate.opsForValue().set(SystemConstant.REDIS_WEEK, week + 1);

        clockService.lambdaUpdate().setSql("total_duration = 0").update();
    }
}
