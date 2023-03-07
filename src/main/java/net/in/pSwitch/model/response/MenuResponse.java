package net.in.pSwitch.model.response;

import lombok.Data;
import net.in.pSwitch.model.Menu;

import java.util.List;

@Data
public class MenuResponse {
    private long menuId;
    private String menuName;
    private String menuType = "page";
    private String pageUrl;
    private String menuIcon;
    private Long parentMenuId;
    private List<Menu> menus;
    private long isSearchable = 0;

}
