package club.looli.ssm.base_ssm_project.entity;

import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User {
    private Integer id; //id
    private String username; //用户名
    private String name; //真实姓名
    private String password; //密码
    private Integer roleId; //角色Id
    private String photo; // 头像
    private Integer sex; //性别 1男 0女
    private Integer age; //年龄
    private String address; //地址
}
