package club.looli.ssm.base_ssm_project.service;

import club.looli.ssm.base_ssm_project.entity.Log;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 日志业务接口
 */
public interface LogService {

    /**
     * 添加日志信息
     * @param log
     * @return
     */
    int add(Log log);

    /**
     * 查询日志信息
     * @param map
     * @return
     */
    List<Log> findList(Map<String,Object> map);

    /**
     * 获取要显示的日志总数
     * @param content
     * @return
     */
    int findCount(String content);

    /**
     * 批量删除日志信息
     * @param logIds
     * @return
     */
    int delete(Integer[] logIds);
}
