package cn.nineSeven.service;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.ClockHistory;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (ClockHistory)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 16:03:25
 */
public interface ClockHistoryService extends IService<ClockHistory> {

    Result list(Integer week, Integer grade, Integer pageNum, Integer pageSize);
}

