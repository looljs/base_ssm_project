package club.looli.ssm.base_ssm_project.service;

import club.looli.ssm.base_ssm_project.entity.Authority;
import club.looli.ssm.base_ssm_project.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    int add(Menu menu);

    /**
     * 获取菜单
     * @param map
     * @return
     */
    List<Menu> findList(Map<String,Object> map);

    /**
     * 获取要显示的菜单总数
     * @param menuName
     * @return
     */
    int findCount(String menuName);


    /**
     * 获取顶级菜单列表
     * @return
     */
    List<Menu> findTopMenuList();

    /**
     * 根据菜单名获取菜单信息
     * 用来判断菜单名是否已经存在
     * @param menuName
     * @return
     */
    Menu findMenuByMenuName(String menuName);

    /**
     * 获取id
     * @return
     */
    int getId();

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    int edit(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    int delete(Integer id);

    int selectSubmenu(Integer id);

    /**
    * @Description 获取所有菜单
    * @Author: looljs
    * @Date   2019/11/17 11:55
    * @Param
    * @Return
    */
    List<Menu> getAll();

    /**
     * 查询角色所有的顶级菜单信息
     * @param authorityList
     * @return
     */
    List<Menu> findAllById(List<Authority> authorityList);

    /**
     * 查询角色所有的子菜单信息
     * @param authorityList
     * @return
     */
    List<Menu> findchildListById(List<Authority> authorityList);
}
