package club.looli.ssm.base_ssm_project.service;

import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.entity.User;

import java.util.List;
import java.util.Map;

/**
 *  @author: looljs
 *  @Date: 2019/11/17 15:22
 *  @Description: 用户业务接口
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param username 根据用户名
     * @return 返回用户信息
     */
    User findByUsername(String username);

    /**
     * 获取用户信息
     * @param username 根据用户名
     * @return 返回用户信息
     */
    User findUserByUsername(String username);
    /**
     * 查询所有符合条件的用户
     * @param search
     * @return
     */
    List<User> findList(Map<String, Object> search);

    /**
     * 查询符合条件的记录总数
     * @param search
     * @return
     */
    int findCount(Map<String, Object> search);

    /**
     * 保存用户信息
     * @param user 用户实体
     * @return 返回修改行数
     */
    int add(User user);

    /**
     * 修改用户信息
     * @param user 用户实体
     * @return 返回修改行数
     */
    int edit(User user);

    /**
     * 批量删除用户信息
     * @param userIds 用户id集合
     * @return 返回修改行数
     */
    int delete(Integer[] userIds);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int editPassword(User user);
}
