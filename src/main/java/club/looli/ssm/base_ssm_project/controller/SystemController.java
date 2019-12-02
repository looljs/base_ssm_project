package club.looli.ssm.base_ssm_project.controller;

import club.looli.ssm.base_ssm_project.entity.Authority;
import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.entity.User;
import club.looli.ssm.base_ssm_project.service.AuthorityService;
import club.looli.ssm.base_ssm_project.service.MenuService;
import club.looli.ssm.base_ssm_project.service.RoleService;
import club.looli.ssm.base_ssm_project.service.UserService;
import club.looli.ssm.base_ssm_project.utils.CpachaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthorityService authorityService;

//    /**
//     * 跳转到首页
//     * @param modelAndView
//     * @return
//     */
//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    public ModelAndView index(ModelAndView modelAndView){
//        modelAndView.setViewName("index");
//        return modelAndView;
//    }

//    /**
//     * 加载欢迎页
//     * @param modelAndView
//     * @return
//     */
//    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
//    public ModelAndView welcome(ModelAndView modelAndView){
//        modelAndView.setViewName("welcome");
//        return modelAndView;
//    }

    /**
     * 跳转到登录页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login_page");
        return modelAndView;
    }


    /**
     * 登出功能
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(
            HttpServletRequest request
    ){
        //将用户信息存入session
        request.getSession().setAttribute("username",null);
        request.getSession().setAttribute("menuListTop",null);
        request.getSession().setAttribute("childList",null);
        return "redirect:/system/login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginAuthentication(
            @RequestParam(name = "username",defaultValue = "") String username,
            @RequestParam(name = "password",defaultValue = "") String password,
            @RequestParam(name = "cpacha",defaultValue = "") String cpacha,
            HttpServletRequest request
    ){
        Map<String,String> maps = new HashMap<>();
        if (StringUtils.equals("",username)){
            maps.put("type","error");
            maps.put("msg","用户名不能为空");
            return maps;
        }
        if (StringUtils.equals("",password)){
            maps.put("type","error");
            maps.put("msg","密码不能为空");
            return maps;
        }
        if (StringUtils.equals("",cpacha)){
            maps.put("type","error");
            maps.put("msg","验证码不能为空");
            return maps;
        }
        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)){
            maps.put("type","error");
            maps.put("msg","登录信息失效");
            return maps;
        }
        if (!StringUtils.equals(loginCpacha.toLowerCase(),cpacha.toLowerCase())){
            maps.put("type","error");
            maps.put("msg","验证码错误");
            return maps;
        }

        //根据用户名查询用户信息
        User user = userService.findByUsername(username);
        if (user == null){
            maps.put("type","error");
            maps.put("msg","用户名不存在");
            return maps;
        }
        if (!StringUtils.equals(username,user.getUsername())){
            maps.put("type","error");
            maps.put("msg","用户名或密码错误");
            return maps;
        }
        if (!StringUtils.equals(password,user.getPassword())){
            maps.put("type","error");
            maps.put("msg","用户名或密码错误");
            return maps;
        }
        List<Menu> menuListTop = new ArrayList<>();
        List<Menu> childListTop = new ArrayList<>();
        //查询用户所属角色的权限
        List<Authority> authorityList = authorityService.findAuthorityByRoleId(user.getRoleId());
        if (authorityList != null && authorityList.size()>0){
            //获取所有角色
            menuListTop = menuService.findAllById(authorityList);
            childListTop = menuService.findchildListById(authorityList);
        }
        //将用户信息存入session
        request.getSession().setAttribute("username",user.getUsername());
        request.getSession().setAttribute("menuListTop",menuListTop);
        request.getSession().setAttribute("childList",childListTop);
        //获取二级菜单下的子按钮，并设置进map
        Map<String,List<Menu>> map = new HashMap<>();
        for (Menu menu : menuListTop) {
            for (Menu child:
                 childListTop) {
                if (child.getUrl().contains("/")){
                    if (child.getParentId().equals(menu.getId())){
                        List<Menu> menuList = new ArrayList<>();
                        for (Menu m :
                                childListTop) {
                            if (child.getId().equals(m.getParentId())){
                                menuList.add(m);
                            }
                        }
                        map.put(child.getUrl().substring(child.getUrl().indexOf("/")+1,child.getUrl().lastIndexOf("/")),menuList);
                    }
                }
            }
        }
        request.getSession().setAttribute("map",map);
        maps.put("type","success");
        maps.put("msg","登录成功");
        return maps;
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "get_cpacha", method = RequestMethod.GET)
    public void get_cpacha(
            @RequestParam(name="vl",required=false,defaultValue="4") Integer vcodeLen,
            @RequestParam(name="w",required=false,defaultValue="150") Integer width,
            @RequestParam(name="h",required=false,defaultValue="40") Integer height,
            @RequestParam(name="type",required=true,defaultValue="loginCpacha") String cpachaType,
            HttpServletRequest request, HttpServletResponse response){
        CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen,width,height);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute(cpachaType, generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 修改密码页面
     * @param model
     * @return
     */
    @RequestMapping(value="/edit_password",method=RequestMethod.GET)
    public ModelAndView editPassword(ModelAndView model){
        model.setViewName("edit_password");
        return model;
    }

    /**
     * 修改密码
     * @param newpassword
     * @param oldpassword
     * @param request
     * @return
     */
    @RequestMapping(value="/editPassword",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> editPasswordAct(@RequestParam("newpassword") String newpassword,
                                               @RequestParam("oldpassword") String oldpassword,
                                               HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isEmpty(newpassword)){
            map.put("type", "error");
            map.put("msg", "请填写新密码！");
            return map;
        }
        User username = userService.findByUsername((String) request.getSession().getAttribute("username"));
        if(!username.getPassword().equals(oldpassword)){
            map.put("type", "error");
            map.put("msg", "原密码错误！");
            return map;
        }
        username.setPassword(newpassword);
        if(userService.editPassword(username) <= 0){
            map.put("type", "error");
            map.put("msg", "密码修改失败，请联系管理员！");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "密码修改成功！");
        return map;
    }

}
