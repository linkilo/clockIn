package cn.nineSeven.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserDto {
    private Long id;

    private String nickname;

    private String avatar;

    private String signature;
}
