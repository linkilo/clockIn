package cn.nineSeven.service;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.Clock;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (Clock)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 16:02:03
 */
public interface ClockService extends IService<Clock> {

    Result listAllClock(Integer grade, Integer pageNum, Integer pageSize);

    Result clock(Long id);

    Result getClockById(Long id);

    Result updateDuration(Long id, Integer duration);
}

