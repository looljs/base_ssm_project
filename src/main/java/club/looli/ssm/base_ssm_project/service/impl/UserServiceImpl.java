package club.looli.ssm.base_ssm_project.service.impl;

import club.looli.ssm.base_ssm_project.dao.UserDAO;
import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.entity.User;
import club.looli.ssm.base_ssm_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  @author: looljs
 *  @Date: 2019/11/17 15:23
 *  @Description: 用户业务接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    /**
     * 获取用户信息
     * @param username 根据用户名
     * @return 用户信息
     */
    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 获取用户信息
     * @param username 根据用户名
     * @return 用户信息
     */
    @Override
    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public List<User> findList(Map<String, Object> search) {
        return userDAO.findList(search);
    }

    @Override
    public int findCount(Map<String, Object> search) {
        return userDAO.findCount(search);
    }

    @Override
    public int add(User user) {
        return userDAO.add(user);
    }

    @Override
    public int edit(User user) {
        return userDAO.edit(user);
    }

    @Override
    public int delete(Integer[] userIds) {
        return userDAO.delete(userIds);
    }

    @Override
    public int editPassword(User user) {
        return userDAO.editPassword(user);
    }


}
