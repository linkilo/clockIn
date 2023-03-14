package cn.nineSeven.entity.pojo;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Clock)表实体类
 *
 * @author makejava
 * @since 2023-03-14 16:02:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clock {
    
    private Long id;
    //开始时间
    private Date beginTime;
    //0未打卡,1正在打卡
    private Integer status;
    
    private Integer totalDuration;
    
    private Integer targetDuration;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //逻辑删除
    private Integer delFlag;

}

