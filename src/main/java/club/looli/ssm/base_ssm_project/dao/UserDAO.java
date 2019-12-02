package club.looli.ssm.base_ssm_project.dao;

import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *  @author: looljs
 *  @Date: 2019/11/17 15:22
 *  @Description: 用户DAO接口
 */
@Mapper
@Repository
public interface UserDAO {

    /**
     * 登录使用
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Select("select roleId,username,password from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    /**
     * 判断用户是否存在
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Select("select id,username,password,roleId,photo,sex,age,address from user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

    /**
     * 查询符合条件的用户
     * @param search
     * @return
     */
    @Select({
            "<script>",
            "select id,username,password,roleId,photo,sex,age,address from user where username like #{username}",
            "<if test='sex != null'>",
            "AND sex = #{sex}",
            "</if>",
            "<if test='roleId != null'>",
            "AND roleId = #{roleId}",
            "</if>",
            "limit #{start} , #{size}",
            "</script>",
    })
    List<User> findList(Map<String, Object> search);

    /**
     * 查询符合条件的用户总数
     * @param search
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from user where username like #{username}",
            "<if test='sex != null'>",
                "AND sex = #{sex}",
            "</if>",
            "<if test='roleId != null'>",
                "AND roleId = #{roleId}",
            "</if>",
            "</script>",
    })
    int findCount(Map<String, Object> search);

    /**
     * 保存用户信息
     * @param user 用户实体
     * @return 返回修改行数
     */
    @Insert("insert into user (username,password,roleId,photo,sex,age,address) values " +
            "(#{username},#{password},#{roleId},#{photo},#{sex},#{age},#{address})")
    int add(User user);

    /**
     * 修改用户信息
     * @param user 用户实体
     * @return 返回修改行数
     */
    @Update("update user set username=#{username}," +
            "roleId=#{roleId},photo=#{photo},sex=#{sex}," +
            "age=#{age},address=#{address} where id = #{id}")
    int edit(User user);

    /**
     * 批量删除用户信息
     * @param userIds 用户id集合
     * @return 返回修改行数
     */
    @Delete({
            "<script>",
            "delete from user where id in (",
            "<foreach item='id' collection='ids' separator=','>",
                "#{id}",
            "</foreach>",
            ")",
            "</script>"
    })
    int delete(@Param("ids") Integer[] userIds);

    /**
     * 修改密码
     * @param user
     * @return
     */
    @Update("update user set password = #{password} where username = #{username}")
    int editPassword(User user);
}
