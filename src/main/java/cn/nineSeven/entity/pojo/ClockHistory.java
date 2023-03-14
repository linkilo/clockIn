package cn.nineSeven.entity.pojo;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ClockHistory)表实体类
 *
 * @author makejava
 * @since 2023-03-14 16:03:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClockHistory {
    
    private Long id;
    //周数
    private Integer week;
    //时长
    private Integer duration;
    //0未达标,1达标
    private Integer isStandard;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //逻辑删除
    private Integer delFlag;

}

