package club.looli.ssm.base_ssm_project.entity;

import lombok.Data;

/**
 *  @author: looljs
 *  @Date: 2019/11/16 18:56
 *  @Description: 权限实体
 */
@Data
public class Authority {
    private Integer id;//id
    private Integer roleId; //角色id
    private Integer menuId; //菜单id
}
