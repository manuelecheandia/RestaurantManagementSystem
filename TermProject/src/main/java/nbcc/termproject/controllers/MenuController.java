package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.Event;
import nbcc.termproject.entities.Menu;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.MenuItemRepository;
import nbcc.termproject.repositories.MenuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class MenuController {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    private  final LoginInfo loginInfo;

    public MenuController(MenuRepository menuRepository, MenuItemRepository menuItemRepository, LoginInfo loginInfo) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping("menus")
    public String getAllMenu(Model model){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var values = menuRepository.findAll();
            model.addAttribute("menus", values);
            return "menu/index";

        }else{
            return "login/login";
        }


    }

    @GetMapping(value = "/menu/create")
    public String create(Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute("menu", new Event());
            model.addAttribute("menuItems", menuItemRepository.findAll());
            return "menu/create";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value = "/menu/create")
    public String createMenu(@Valid Menu menu, BindingResult br, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            if(!br.hasErrors()){
                menu.setDateCreated(LocalDate.now());
                menuRepository.save(menu);
                return "redirect:/menus";
            }else{
                model.addAttribute("menuItems", menuItemRepository.findAll());
                return "menu/create";
            }
        }else{
            return "login/login";
        }

    }

    @GetMapping(value= "/menu/edit/{id}")
    public String edit(Model model, @PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menu = menuRepository.findById(id);

            if(menu.isPresent()){
                model.addAttribute("menu", menu.get());
                model.addAttribute("menuItems", menuItemRepository.findAll());
            }

            return "menu/edit";

        }else{
            return "login/login";
        }


    }
    @PostMapping(value = "/menu/edit/{id}")
    public String editMenu(@PathVariable int id, @Valid Menu menu, Model model) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            menuRepository.updateMenu(id,menu.getName(),menu.getDescription());

            return "redirect:/menus";

        }else{
            return "login/login";
        }


    }

    @GetMapping(value = "/menu/{id}")
    public String details(@PathVariable int id, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menu = menuRepository.findById(id);
            var menuItem = menuItemRepository.findAllByMenuId(menu.get().getId());


            menu.ifPresent(value -> model.addAttribute("menu", value));

            if (!menuItem.isEmpty()) {
                model.addAttribute("menuItems", menuItem);
            }

            return "menu/detail";

        }else{
            return "login/login";
        }


    }

    @GetMapping(value = "/menu/delete/{id}")
    public String delete( Model model,@PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var menu = menuRepository.findById(id);

            menu.ifPresent(value -> model.addAttribute("menu", value));

            return "menu/delete";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value = "/menu/delete/{id}")
    public String deleteMenu(@PathVariable int id,  Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){

            menuRepository.deleteById(id);
            return "redirect:/menus";

        }else{
            return "login/login";
        }

    }

}
