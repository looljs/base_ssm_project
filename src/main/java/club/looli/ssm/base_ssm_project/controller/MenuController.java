package club.looli.ssm.base_ssm_project.controller;

import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.page.Page;
import club.looli.ssm.base_ssm_project.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    //当前操作系统文件分隔符
    private String sepa = java.io.File.separator;

    @Autowired
    private MenuService menuService;


    /**
     * 点击菜单管理
     * @param modelAndView
     * @return 菜单管理页面
     * registry.addViewController("/menu/").setViewName("menu/menu_list");
     */
    @RequestMapping(value = "/menuList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView,HttpServletRequest request){
        //
        Map<String,List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("menu");
//        request.getSession().setAttribute("menu",menuList);
        //
        modelAndView.addObject("menu",menuList);
        modelAndView.setViewName("menu/menu_list");
        return modelAndView;
    }

    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> menuList(
            Page page,
            @RequestParam(name = "menuName",defaultValue = "",required = false) String menuName,
            HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();
        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("name","%"+menuName+"%");
        List<Menu> data = menuService.findList(search);
        int count = menuService.findCount("%" + menuName + "%");
        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        return map;
    }

    /**
     * 添加菜单信息
     * @param menu 菜单信息
     * @return 返回执行信息
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Menu menu){
        Map<String,String> map = new HashMap<>();
        if (menu == null){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        if (StringUtils.equals("",menu.getName())||StringUtils.equals("",menu.getIcon())||StringUtils.equals("",menu.getUrl())){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        //判断菜单名是否可用
        Menu menuName = menuService.findMenuByMenuName(menu.getName());
        if (menuName != null){
            map.put("type","error");
            map.put("msg","菜单名已经存在");
            return map;
        }
        //顶级菜单设置父菜单为0
        if (menu.getParentId()==null){
            menu.setParentId(0);
        }
        //设置id
        menu.setId(menuService.getId()+1);
        menuService.add(menu);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 菜单分两级
     * 顶级： 其上没有父菜单
     * 二级： 顶级菜单下
     * 选择上级菜单时，只能选择顶级菜单，不选，则默认为当前添加菜单为顶级菜单
     * @param menu
     * @return
     */
    @RequestMapping(value = "/topMenus",method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getTopMenu(Menu menu){
        return menuService.findTopMenuList();
    }

    /**
     * 获取所有icon
     * @return
     */
    @RequestMapping(value = "/icons",method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> getIcons(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //获取icons路径
        String property = System.getProperty("user.dir");//获取当前项目相对路径
        File file = new File( property + sepa +
                                        "src" + sepa +
                                        "main" + sepa +
                                        "resources" + sepa +
                                        "static" + sepa +
                                        "easyui" + sepa +
                                        "css" + sepa +
                                        "icons");
        //判断路径是否存在
        if (!file.exists()){
            map.put("type","error");
            map.put("msg","文件或目录不存在");
            return map;
        }
        List<String> icons = new ArrayList<>();
        String[] list = file.list();
        if (list != null){
            for (String s :
                    list) {
                if (s!=null && s.contains("png")){
                    icons.add("icon-" + s.substring(0,s.indexOf(".")).replace("_","-"));
                }
            }
        }
        map.put("type","success");
        map.put("data",icons);
        return map;
    }

    /**
     * 修改菜单信息
     * @param menu
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(Menu menu){
        Map<String,Object> map = new HashMap<>();
        menuService.edit(menu);
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(name = "id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (menuService.selectSubmenu(id)>0){
            map.put("type","error");
            map.put("msg","该菜单下存有子菜单，无法直接删除");
            return map;
        }
        menuService.delete(id);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
