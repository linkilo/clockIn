package cn.nineSeven.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClockInfoVo {
    private Integer status;

    private Integer totalDuration;

    private Integer targetDuration;
}
