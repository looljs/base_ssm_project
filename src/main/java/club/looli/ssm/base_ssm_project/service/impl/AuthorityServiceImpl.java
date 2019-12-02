package club.looli.ssm.base_ssm_project.service.impl;

import club.looli.ssm.base_ssm_project.dao.AuthorityDAO;
import club.looli.ssm.base_ssm_project.entity.Authority;
import club.looli.ssm.base_ssm_project.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author: looljs
 *  @Date: 2019/11/16 18:58
 *  @Description: 权限业务接口实现
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDAO authorityDAO;

    @Override
    public int addAuthority(List<Authority> authority) {
        return authorityDAO.add(authority);
    }

    @Override
    public int deleteAuthorityByRoleId(Integer roleId) {
        return authorityDAO.delete(roleId);
    }

    @Override
    public List<Authority> findAuthorityByRoleId(Integer roleId) {
        return authorityDAO.selectByRoleId(roleId);
    }
}
