package cn.nineSeven.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClockInfoVo {

    private String nickname;

    private String avatar;

    private Integer status;

    private Integer totalDuration;

    private Integer targetDuration;

}
