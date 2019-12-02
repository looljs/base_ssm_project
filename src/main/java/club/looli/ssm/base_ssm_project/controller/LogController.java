package club.looli.ssm.base_ssm_project.controller;

import club.looli.ssm.base_ssm_project.entity.Log;
import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.page.Page;
import club.looli.ssm.base_ssm_project.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志控制器类
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 跳转用户列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/logList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("log");
        modelAndView.addObject("log",menuList);
        modelAndView.setViewName("log/log_list");
        return modelAndView;
    }

    /**
     * 获取日志列表
     * @param page
     * @param content
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name="content",required=false,defaultValue="") String content
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("content", "%"+content.trim()+"%");
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        map.put("rows", logService.findList(queryMap));
        map.put("total", logService.findCount("%"+content.trim()+"%"));
        return map;
    }

    /**
     * 添加日志
     * @param log
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(Log log){
        Map<String, String> map = new HashMap<String, String>();
        if(log == null){
            map.put("type", "error");
            map.put("msg", "请填写正确的日志信息！");
            return map;
        }
        if(StringUtils.isEmpty(log.getContent())){
            map.put("type", "error");
            map.put("msg", "请填写日志内容！");
            return map;
        }
        log.setCreateTime(new Date());
        if(logService.add(log) <= 0){
            map.put("type", "error");
            map.put("msg", "日志添加失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "日志添加成功！");
        return map;
    }

    /**
     * 批量删除日志
     * @param ids
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(@RequestParam("ids[]") Integer[] ids){
        Map<String, String> map = new HashMap<String, String>();
        if(ids == null || ids.length <= 0){
            map.put("type", "error");
            map.put("msg", "选择要删除的数据！");
            return map;
        }
        if(logService.delete(ids) <= 0){
            map.put("type", "error");
            map.put("msg", "日志删除失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "日志删除成功！");
        return map;
    }

}
