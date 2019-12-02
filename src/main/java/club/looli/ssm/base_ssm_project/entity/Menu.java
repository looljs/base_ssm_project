package club.looli.ssm.base_ssm_project.entity;

import lombok.Data;

@Data
public class Menu {
    private Integer id;
    private Integer parentId;//上级菜单id
    private Integer _parentId;//上级菜单id映射到EasyUI
    private String name;//菜单名称
    private String url;//菜单url
    private String icon;//菜单图标

    public Integer get_parentId() {
        this._parentId = this.parentId;
        return _parentId;
    }

    public void set_parentId(Integer _parentId) {
        this._parentId = _parentId;
    }
}
