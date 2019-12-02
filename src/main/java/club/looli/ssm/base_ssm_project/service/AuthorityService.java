package club.looli.ssm.base_ssm_project.service;

import club.looli.ssm.base_ssm_project.entity.Authority;

import java.util.List;

/**
 *  @author: looljs
 *  @Date: 2019/11/16 18:58
 *  @Description: 权限业务接口
 */
public interface AuthorityService {

    /**
    * @Description 添加权限
    * @Author: looljs
    * @Date   2019/11/17 11:27
    * @Param  List<Authority> authority
    * @Return
    */
    int addAuthority(List<Authority> authority);
    
    /**
    * @Description 删除权限根据角色id
    * @Author: looljs
    * @Date   2019/11/17 11:28
    * @Param  roleId
    * @Return      
    */
    int deleteAuthorityByRoleId(Integer roleId);

    /**
    * @Description 获取角色拥有的权限
    * @Author: looljs
    * @Date   2019/11/17 11:30
    * @Param  roleId 角色id
    * @Return
    */
    List<Authority> findAuthorityByRoleId(Integer roleId);
}
