package club.looli.ssm.base_ssm_project.entity;

import lombok.Data;

/**
 * 角色实体
 */
@Data
public class Role {
    private Integer id;//角色id
    private String name;//角色名
    private String remark;//角色备注

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
