package cn.nineSeven.service.impl;

import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.Clock;
import cn.nineSeven.entity.vo.ClockInfoVo;
import cn.nineSeven.entity.vo.PageVo;
import cn.nineSeven.entity.vo.StartClockVo;
import cn.nineSeven.entity.vo.StopClockVo;
import cn.nineSeven.mapper.ClockMapper;
import cn.nineSeven.service.ClockService;
import cn.nineSeven.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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
        PageVo pageVo = new PageVo(clockInfoVos, clockInfoVos.size());
        return Result.okResult(pageVo);
    }

    @Override
    public Result clock(Long id) {
        Clock clock = getById(id);
        int status = clock.getStatus();
        if(status == 0) {
            clock.setStatus(1);
            clock.setBeginTime(LocalDateTime.now());
            updateById(clock);

            StartClockVo startClockVo = BeanCopyUtils.copyBean(clock, StartClockVo.class);
            return Result.okResult(startClockVo);
        }

        long duration = ChronoUnit.MINUTES.between(clock.getBeginTime(), LocalDateTime.now());

        if(duration >= 60 * 6){
            return Result.errorResult(AppHttpCodeEnum.CLOCK_TIMEOUT);
        }
        clock.setTotalDuration((int) duration+ clock.getTotalDuration());
        clock.setStatus(0);


        updateById(clock);
        StopClockVo stopClockVo = BeanCopyUtils.copyBean(clock, StopClockVo.class);
        return Result.okResult(stopClockVo);
    }
}

