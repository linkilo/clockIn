package cn.nineSeven.entity.pojo;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-03-14 15:56:54
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    @TableId
    private Long id;
    //用户名
    private String username;
    //昵称
    private String nickname;
    //密码
    private String password;
    //头像
    private String avatar;
    //年级
    private Integer grade;
    //签名
    private String signature;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //逻辑删除
    private Integer delFlag;

}

