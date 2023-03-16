package cn.nineSeven.entity.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId
    private Long id;
    //开始时间
    private LocalDateTime beginTime;
    //0未打卡,1正在打卡
    private Integer status;
    
    private Integer totalDuration;
    
    private Integer targetDuration;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //逻辑删除
    private Integer delFlag;

    public Clock(LocalDateTime beginTime, Integer status, Integer totalDuration, Integer targetDuration) {
        this.beginTime = beginTime;
        this.status = status;
        this.totalDuration = totalDuration;
        this.targetDuration = targetDuration;
    }
}

