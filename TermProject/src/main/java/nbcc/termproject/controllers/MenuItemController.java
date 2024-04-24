package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.MenuItem;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.MenuItemRepository;
import nbcc.termproject.repositories.MenuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuItemController {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    private  final LoginInfo loginInfo;

    public MenuItemController(MenuRepository menuRepository, MenuItemRepository menuItemRepository, LoginInfo loginInfo) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping("menuItems")
    public String getAllMenuItems(Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var values = menuItemRepository.findAll();
            model.addAttribute("menuItems", values);
            return "menuItem/index";

        }else{
            return "login/login";
        }

    }
    @GetMapping("/menuItem/create/{id}")
    public String create(Model model, @PathVariable int id){


        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menu = menuRepository.findById(id);

            model.addAttribute("menuItem", new MenuItem());
            model.addAttribute("menu", menu.get());

            return "menuItem/create";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value= "/menuItem/create/{id}")
    public String createMenuItem(@Valid MenuItem menuItem, BindingResult br, @PathVariable int id, Model model) {


        var menu = menuRepository.findById(id);
        MenuItem newMenuItem = new MenuItem();

        if(!br.hasErrors()){
            newMenuItem.setMenu(menu.get());
            newMenuItem.setName(menuItem.getName());
            newMenuItem.setDescription(menuItem.getDescription());
            menuItemRepository.save(newMenuItem);
            return "redirect:/menu/" + id;
        } else {

            model.addAttribute("menu", menu.get());

            return "menuItem/create";
        }

    }

    @GetMapping(value= "/menuItem/edit/{id}")
    public String edit(Model model, @PathVariable int id){


        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menuItem = menuItemRepository.findById(id);
            var menu = menuRepository.findById(menuItem.get().getMenu().getId());

            model.addAttribute("menuItem", menuItem.get());
            model.addAttribute("menu", menuItem.get().getMenu());

            return "menuItem/edit";

        }else{
            return "login/login";
        }

    }

    @PostMapping(value = "/menuItem/edit/{id}")
    public String edit(@PathVariable int id, @Valid MenuItem menuItem, Model model) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menuId = menuItem.getMenu().getId();

            if(menuItem.getId() == id){
                menuItemRepository.update(id,menuItem.getName(), menuItem.getDescription());

            }

            return "redirect:/menu/" + menuId;

        }else{
            return "login/login";
        }



    }

    @GetMapping(value = "/menuItem/delete/{id}")
    public String delete( Model model,@PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menuItem = menuItemRepository.findById(id);

            menuItem.ifPresent(value -> model.addAttribute("menuItem", value));

            return "menuItem/delete";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value = "/menuItem/delete/{id}")
    public String deleteConfirm(@PathVariable int id,  Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            menuItemRepository.deleteById(id);

            return "redirect:/menus";

        }else{
            return "login/login";
        }


    }

    @GetMapping(value = "/menuItem/{id}")
    public String details(@PathVariable int id, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menuItem = menuItemRepository.findById(id);

            menuItem.ifPresent(value -> model.addAttribute("menuItem", value));
            return "menuItem/detail";

        }else{
            return "login/login";
        }


    }
}
