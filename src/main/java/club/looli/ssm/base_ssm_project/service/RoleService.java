package club.looli.ssm.base_ssm_project.service;

import club.looli.ssm.base_ssm_project.entity.Role;
import org.apache.ibatis.annotations.Delete;

import java.util.List;
import java.util.Map;

/**
 * 角色业务接口
 */
public interface RoleService {
   
    /**
    * @Description 获取当前页面角色信息
    * @Author: looljs
    * @Date   2019/11/16 15:57
    * @Param  search
    * @Return List<Role>
    */
    List<Role> findList(Map<String, Object> search);

   /**
   * @Description 获取总数
   * @Author: looljs
   * @Date   2019/11/16 15:56
   * @Param  name
   * @Return     int
   */
    int findCount(String name);

    /**
    * @Description 根据角色名获取角色
    * @Author: looljs
    * @Date   2019/11/16 18:27
    * @Param  name
    * @Return     role
    */
    Role findRoleByRoleName(String name);

    /**
    * @Description 添加角色
    * @Author: looljs
    * @Date   2019/11/16 18:27
    * @Param  role
    * @Return
    */
    int add(Role role);

    /**
    * @Description 修改角色信息
    * @Author: looljs
    * @Date   2019/11/16 18:34
    * @Param  role
    * @Return    int
    */
    int edit(Role role);

    /**
    * @Author: looljs
    * @Date   2019/11/16 18:48
    * @Param  id
    * @Return
    */
    int delete(Integer id);

    /**
    * @Description 查看是否有权限
    * @Author: looljs
    * @Date   2019/11/16 18:49
    * @Param
    * @Return      
    */
    int hasPermission(Integer id);

    /**
     * 获取所有角色
     * @return
     */
    List<Role> findAll();

}
