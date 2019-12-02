package club.looli.ssm.base_ssm_project.controller;

import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.entity.User;
import club.looli.ssm.base_ssm_project.page.Page;
import club.looli.ssm.base_ssm_project.service.RoleService;
import club.looli.ssm.base_ssm_project.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //当前操作系统分隔符
    private String sepa = java.io.File.separator;

    @Autowired
    private UserService userService;

    /**
     * 跳转用户列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        //
        Map<String,List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("user");
//        request.getSession().setAttribute("menu",menuList);
        //
        modelAndView.addObject("user",menuList);
        modelAndView.setViewName("user/user_list");
        return modelAndView;
    }

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户信息
     * @param page 分页信息
     * @param username 用户名
     * @param sex 性别
     * @param roleId 角色Id
     * @return 返回符合条件的用户信息
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> menuList(
            Page page,
            @RequestParam(name = "username",defaultValue = "",required = false) String username,
            @RequestParam(name = "sex", defaultValue = "" , required = false) String sex,
            @RequestParam(name = "roleId",defaultValue = "",required = false) String roleId,
            HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();
        if (sex.equals("")){
            sex = null;
        }
        if (roleId.equals("")){
            roleId = null;
        }
        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("username","%"+username.trim()+"%");
        search.put("sex",sex);
        search.put("roleId",roleId);
        List<User> data = userService.findList(search);
        int count = userService.findCount(search);
        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        //
        Map<String,List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("user");
        request.getSession().setAttribute("user",menuList);
        //
        return map;
    }

    /**
     * 添加用户
     * @param user 前台提交的用户信息
     * @return 返回信息
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(User user){
        Map<String,Object> map = new HashMap<>();
        if (user == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //判断用户是否存在
        User username = userService.findByUsername(user.getUsername());
        if (username != null){
            map.put("type","error");
            map.put("msg","用户名已存在！");
            return map;
        }
        //添加用户
        userService.add(user);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改用户
     * @param user 前台提交的用户信息
     * @return 返回信息
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(User user){
        Map<String,Object> map = new HashMap<>();
        if (user == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //判断用户是否存在
        User username = userService.findUserByUsername(user.getUsername());
        if (username != null && !username.getId().equals(user.getId())){
            map.put("type","error");
            map.put("msg","用户名已存在！");
            return map;
        }
        //修改用户
        userService.edit(user);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 删除用户
     * @param ids 要删除的用户的id集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestParam("ids[]") Integer[] ids){
        Map<String,Object> map = new HashMap<>();
        if (ids == null || ids.length <= 0){
            map.put("type","error");
            map.put("msg","请选择删除的对象！");
            return map;
        }
        userService.delete(ids);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }


    /**
     * 头像上传
     * @param multipartFile
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    public Map<String,Object> uploadPhoto(@RequestParam("photo") MultipartFile multipartFile,
                                          HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //images
        String realPath = request.getSession().getServletContext().getRealPath("/")+"images"+sepa;
        File folder = new File(realPath);
        //不是目录,创建成目录
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        //返回客户端文件系统中的原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()+
                originalFilename.substring(
                        originalFilename.lastIndexOf('.'),
                        originalFilename.length());
        try {
            multipartFile.transferTo(new File(folder,newName));
            map.put("type","success");
            map.put("filepath",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/images"+"/"+newName);
            map.put("msg","上传成功");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("type","error");
        map.put("msg","上传失败");
        return map;
    }





}
