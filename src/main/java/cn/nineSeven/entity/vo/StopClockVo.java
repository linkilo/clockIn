package cn.nineSeven.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StopClockVo {
    private Long id;

    private Integer totalDuration;

    private Integer status;
}
