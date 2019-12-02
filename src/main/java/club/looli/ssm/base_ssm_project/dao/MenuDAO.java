package club.looli.ssm.base_ssm_project.dao;

import club.looli.ssm.base_ssm_project.entity.Authority;
import club.looli.ssm.base_ssm_project.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MenuDAO {

    /**
     * 保存菜单信息
     * @param menu
     * @return
     */
    @Insert("insert into menu (id,parentId,name,url,icon) values (#{id},#{parentId},#{name},#{url},#{icon})")
    int add(Menu menu);

    /**
     * 获取菜单
     * @param map
     * @return
     *
     * 去除分页
     */
//    @Select("select id,parentId,name,url,icon from menu where id != 0 and name like #{name} limit #{start} , #{size}")
    @Select("select id,parentId,name,url,icon from menu where id != 0 and name like #{name} ")
    List<Menu> findList(Map<String,Object> map);

    /**
     * 获取要显示的菜单总数
     * @param menuName
     * @return
     */
    @Select("select count(id) from menu where id != 0 and name like #{menuName}")
    int findCount(String menuName);

    /**
     * 获取顶级菜单列表
     * @return
     */
    @Select("select id,parentId,name,url,icon from menu where parentId = 0")
    List<Menu> findTopMenuList();

    /**
     * 根据菜单名获取菜单信息
     * @param menuName
     * @return
     */
    @Select("select id,parentId,name,url,icon from menu where id != 0 and name = #{menuName}")
    Menu findMenuByMenuName(String menuName);

    /**
     * 获取表中最大的id
     * @return
     */
    @Select("select max(id) from menu")
    int getId();

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @Update("update menu set parentId=#{parentId},name=#{name},url=#{url},icon=#{icon} where id=#{id}")
    int update(Menu menu);

    @Delete("delete from menu where id = #{id}")
    int delete(Integer id);

    @Select("select count(id) from menu where parentId = #{id}")
    int selectSubmenu(Integer id);

    /**
    * @Description 查询所有菜单
    * @Author: looljs
    * @Date   2019/11/17 11:57
    * @Param
    * @Return
    */
    @Select("select id,parentId,name,url,icon from menu where id != 0")
    List<Menu> getAll();

    /**
     * 查询角色拥有的菜单
     * @param authorityList
     * @return
     */
    @Select({
            "<script>"
            ,"select id,parentId,name,url,icon from menu where id != 0 and parentId =0 and id in (",
            "<foreach item='id' collection='ids' separator=','>",
                    "#{id.menuId}",
            "</foreach>",
            ")",
            "</script>"
    })
    List<Menu> findAllById(@Param("ids") List<Authority> authorityList);

    @Select({
            "<script>"
            ,"select id,parentId,name,url,icon from menu where id != 0 and parentId !=0 and id in (",
            "<foreach item='id' collection='ids' separator=','>",
            "#{id.menuId}",
            "</foreach>",
            ")",
            "</script>"
    })
    List<Menu> findchildListById(@Param("ids") List<Authority> authorityList);
}
