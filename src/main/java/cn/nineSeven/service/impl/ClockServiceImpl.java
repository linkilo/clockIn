package cn.nineSeven.service.impl;

import cn.nineSeven.entity.pojo.Clock;
import cn.nineSeven.mapper.ClockMapper;
import cn.nineSeven.service.ClockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (Clock)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 16:02:03
 */
@Service("clockService")
public class ClockServiceImpl extends ServiceImpl<ClockMapper, Clock> implements ClockService {

}

