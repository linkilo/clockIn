package cn.nineSeven.service.impl;

import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.Clock;
import cn.nineSeven.entity.vo.*;
import cn.nineSeven.mapper.ClockMapper;
import cn.nineSeven.service.ClockService;
import cn.nineSeven.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Clock)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 16:02:03
 */
@Service("clockService")
public class ClockServiceImpl extends ServiceImpl<ClockMapper, Clock> implements ClockService {

    @Override
    public Result listAllClock(Integer grade, Integer pageNum, Integer pageSize) {
        ClockMapper mapper = getBaseMapper();
        List<ClockInfoVo> clockInfoVos = mapper.selectAllClock(grade, (pageNum - 1) * pageSize, pageSize);
        List<ClockListInfoVo> clockListInfoVo = clockInfoVos.stream()
                .map(i -> {
                    if (i.getStatus() == 1) {
                        i.setTotalDuration((int) ChronoUnit.MINUTES.between(i.getBeginTime(), LocalDateTime.now()) + i.getTotalDuration());
                    }
                    return BeanCopyUtils.copyBean(i, ClockListInfoVo.class);
                }).collect(Collectors.toList());
        PageVo pageVo = new PageVo(clockListInfoVo, clockInfoVos.size());
        return Result.okResult(pageVo);
    }

    @Override
    public Result clock(Long id) {
        Clock clock = getById(id);
        int status = clock.getStatus();
        if(status == SystemConstant.CLOCKED_STATUS) {
            clock.setStatus(SystemConstant.CLOCKING_STATUS);
            clock.setBeginTime(LocalDateTime.now());
            updateById(clock);

            StartClockVo startClockVo = BeanCopyUtils.copyBean(clock, StartClockVo.class);
            return Result.okResult(startClockVo);
        }
        clock.setStatus(SystemConstant.CLOCKED_STATUS);
        long duration = ChronoUnit.MINUTES.between(clock.getBeginTime(), LocalDateTime.now());

        if(duration >= 60 * 6){
            updateById(clock);
            return Result.errorResult(AppHttpCodeEnum.CLOCK_TIMEOUT);
        }
        clock.setTotalDuration((int) duration+ clock.getTotalDuration());

        updateById(clock);
        StopClockVo stopClockVo = BeanCopyUtils.copyBean(clock, StopClockVo.class);
        return Result.okResult(stopClockVo);
    }

    @Override
    public Result getClockById(Long id) {
        Clock clock = getById(id);
        if(clock.getStatus() == 1){
            clock.setTotalDuration((int) ChronoUnit.MINUTES.between(clock.getBeginTime(), LocalDateTime.now()) + clock.getTotalDuration());
        }
        UserClockInfoVo userClockInfoVo = BeanCopyUtils.copyBean(clock, UserClockInfoVo.class);
        return Result.okResult(userClockInfoVo);
    }
}

