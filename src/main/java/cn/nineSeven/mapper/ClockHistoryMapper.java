package cn.nineSeven.mapper;

import cn.nineSeven.entity.pojo.ClockHistory;
import cn.nineSeven.entity.vo.ClockHistoryListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * (ClockHistory)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 16:03:25
 */
public interface ClockHistoryMapper extends BaseMapper<ClockHistory> {

    @Select("select user.id, nickname, avatar, week, duration, is_standard\n" +
            "from user left join clock_history on user.id = clock_history.id\n" +
            "where grade = #{grade} and week = #{week} and clock_history = 0\n" +
            "order by duration DESC\n" +
            "limit #{pageNum}, #{pageSize}")
    List<ClockHistoryListVo> selectClockHistoryList(@Param("week") Integer week,@Param("grade") Integer grade,
                                                    @Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);
}

