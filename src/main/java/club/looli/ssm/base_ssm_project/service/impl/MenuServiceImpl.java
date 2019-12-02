package club.looli.ssm.base_ssm_project.service.impl;

import club.looli.ssm.base_ssm_project.dao.MenuDAO;
import club.looli.ssm.base_ssm_project.entity.Authority;
import club.looli.ssm.base_ssm_project.entity.Menu;
import club.looli.ssm.base_ssm_project.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO menuDAO;

    @Transactional
    public int add(Menu menu) {
        return menuDAO.add(menu);
    }

    @Override
    public List<Menu> findList(Map<String, Object> map) {
        return menuDAO.findList(map);
    }

    @Override
    public int findCount(String menuName) {
        return menuDAO.findCount(menuName);
    }

    @Override
    public List<Menu> findTopMenuList() {
        return menuDAO.findTopMenuList();
    }

    @Override
    public Menu findMenuByMenuName(String menuName) {
        return menuDAO.findMenuByMenuName(menuName);
    }

    @Override
    public int getId() {
        return menuDAO.getId();
    }

    @Override
    public int edit(Menu menu) {
        return menuDAO.update(menu);
    }

    @Override
    public int delete(Integer id) {
        return menuDAO.delete(id);
    }

    @Override
    public int selectSubmenu(Integer id) {
        return menuDAO.selectSubmenu(id);
    }

    @Override
    public List<Menu> getAll() {
        return menuDAO.getAll();
    }

    @Override
    public List<Menu> findAllById(List<Authority> authorityList) {
        return  menuDAO.findAllById(authorityList);
    }

    @Override
    public List<Menu> findchildListById(List<Authority> authorityList) {
        return menuDAO.findchildListById(authorityList);
    }

}
