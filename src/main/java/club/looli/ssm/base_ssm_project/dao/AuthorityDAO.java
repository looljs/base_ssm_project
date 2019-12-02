package club.looli.ssm.base_ssm_project.dao;

import club.looli.ssm.base_ssm_project.entity.Authority;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  @author: looljs
 *  @Date: 2019/11/16 18:57
 *  @Description: 权限DAO接口
 */
@Mapper
@Repository
public interface AuthorityDAO {

    /**
    * @Description 添加权限
    * @Author: looljs
    * @Date   2019/11/17 11:33
    * @Param  List<Authority>
    * @Return      
    */
    //"insert into authority (roleId,menuId) values (#{roleId},#{menuId})"
    @Insert({
            "<script>" +
                "insert into authority (roleId,menuId) values",
                "<foreach item=\"item\" collection=\"authority\"\n" +
                        "     separator=\",\">\n" +
                        "       ( #{item.roleId},#{item.menuId})\n" +
                        "  </foreach>",
            "</script>"
    })
    int add(@Param("authority") List<Authority> authority);

    /**
    * @Description 删除权限
    * @Author: looljs
    * @Date   2019/11/17 11:33
    * @Param  roleId
    * @Return      
    */
    @Delete("delete from authority where roleId = #{roleId}")
    int delete(Integer roleId);

    /**
    * @Description 获取权限
    * @Author: looljs
    * @Date   2019/11/17 11:33
    * @Param  roleId
    * @Return      
    */
    @Select("select id,roleId,menuId from authority where roleId = #{roleId}")
    List<Authority> selectByRoleId(Integer roleId);
}
