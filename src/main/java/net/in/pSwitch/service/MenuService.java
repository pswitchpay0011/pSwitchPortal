package net.in.pSwitch.service;

import net.in.pSwitch.model.Menu;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.utility.StringLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenu(){
        return menuRepository.findAll();
    }

    public List<Menu> getMenuByRole(String role){
        if(StringUtils.isEmpty(role)) {
            throw new RuntimeException("Invalid Role");
        }else if (StringLiteral.ROLE_CODE_ADMIN.equalsIgnoreCase(role)) {
            return menuRepository.findAllByAdminMenuAndIsActiveOrderByMenuOrderAsc(1,1);
        }else if (StringLiteral.ROLE_CODE_RETAILER.equalsIgnoreCase(role)) {
            return menuRepository.findAllByRetailerMenuAndIsActiveOrderByMenuOrderAsc(1,1);
        }
        return null;
    }
}
