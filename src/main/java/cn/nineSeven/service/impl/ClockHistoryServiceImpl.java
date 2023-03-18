package cn.nineSeven.service.impl;

import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.ClockHistory;
import cn.nineSeven.entity.vo.ClockHistoryListVo;
import cn.nineSeven.entity.vo.ClockHistoryVo;
import cn.nineSeven.entity.vo.PageVo;
import cn.nineSeven.mapper.ClockHistoryMapper;
import cn.nineSeven.service.ClockHistoryService;
import cn.nineSeven.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clockHistoryService")
public class ClockHistoryServiceImpl extends ServiceImpl<ClockHistoryMapper, ClockHistory> implements ClockHistoryService {

    @Override
    public Result list(Integer week, Integer grade, Integer pageNum, Integer pageSize) {
        ClockHistoryMapper mapper = getBaseMapper();
        List<ClockHistoryListVo> clockHistoryListVos = mapper.selectClockHistoryList(week, grade, (pageNum - 1) * pageSize, pageSize);
        return Result.okResult(clockHistoryListVos);
    }

    @Override
    public Result getClockHistoryById(Long id, Integer pageNum, Integer pageSize) {
        Page<ClockHistory> page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<ClockHistory> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ClockHistory::getId, id);

        page = page(page, lqw);
        List<ClockHistory> clockHistories = page.getRecords();
        List<ClockHistoryVo> clockHistoryVos = BeanCopyUtils.copyBeanList(clockHistories, ClockHistoryVo.class);

        return Result.okResult(new PageVo(clockHistoryVos, page.getTotal()));
    }
}

