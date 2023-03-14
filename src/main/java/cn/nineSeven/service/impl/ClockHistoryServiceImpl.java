package cn.nineSeven.service.impl;

import cn.nineSeven.entity.pojo.ClockHistory;
import cn.nineSeven.mapper.ClockHistoryMapper;
import cn.nineSeven.service.ClockHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (ClockHistory)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 16:03:25
 */
@Service("clockHistoryService")
public class ClockHistoryServiceImpl extends ServiceImpl<ClockHistoryMapper, ClockHistory> implements ClockHistoryService {

}

