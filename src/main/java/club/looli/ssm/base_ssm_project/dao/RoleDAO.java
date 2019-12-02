package club.looli.ssm.base_ssm_project.dao;

import club.looli.ssm.base_ssm_project.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色DAO接口
 */
@Mapper
@Repository
public interface RoleDAO {

    /**
    * @Description 查询符合条件的信息
    * @Author: looljs
    * @Date   2019/11/16 15:59
    * @Param  search
    * @Return      List<Role>
    */
    @Select("select id,name,remark from role where name like #{name} limit #{start} , #{size}")
    List<Role> findList(Map<String, Object> search);

    /**
    * @Description 查询符合条件的总数
    * @Author: looljs
    * @Date   2019/11/16 15:59
    * @Param  name
    * @Return     int
    */
    @Select("select count(id) from role where name like #{name}")
    int findCount(String name);


    /**
     * @Description 根据角色名获取角色
     * @Author: looljs
     * @Date   2019/11/16 18:27
     * @Param  name
     * @Return     role
     */
    @Select("select id,name,remark from role where name = #{name}")
    Role findRoleByRoleName(String name);

    /**
     * @Description 添加角色
     * @Author: looljs
     * @Date   2019/11/16 18:27
     * @Param  role
     * @Return
     */
    @Insert("insert into role" +
            "(name,remark) values (#{name},#{remark}) ")
    int add(Role role);

    /**
    * @Description 修改角色
    * @Author: looljs
    * @Date   2019/11/16 18:48
    * @Param
    * @Return
    */
    @Update("update role set name=#{name},remark=#{remark} where id =#{id}")
    int edit(Role role);

    /**
    * @Description 删除角色
    * @Author: looljs
    * @Date   2019/11/16 18:48
    * @Param
    * @Return
    */
    @Delete("delete from role where  id = #{id}")
    int delete(Integer id);

    @Select("select count(id) from authority where roleId = #{id}")
    int hasPermission(Integer id);

    /**
     * 查询所有角色
     * @return
     */
    @Select("select id,name from role")
    List<Role> findAll();

}
