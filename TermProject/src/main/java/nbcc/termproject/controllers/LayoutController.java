package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.Layout;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.DiningTableRepository;
import nbcc.termproject.repositories.LayoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class LayoutController {

    private final LayoutRepository layoutRepository;

    private final DiningTableRepository diningTableRepository;

    private  final LoginInfo loginInfo;

    public LayoutController(LayoutRepository layoutRepository, DiningTableRepository diningTableRepository, LoginInfo loginInfo) {
        this.layoutRepository = layoutRepository;
        this.diningTableRepository = diningTableRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping(value = "/layouts")
    public String getAll(Model model){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var values = layoutRepository.findAll();
            model.addAttribute("layouts", values);

            return "layout/index";
        }else {
            return "login/login";
        }


    }


    @GetMapping(value = "/layout/create")
    public String create(Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            model.addAttribute("layout", new Layout());

            return "layout/create";
        }else {
            return "login/login";
        }



    }
    @PostMapping(value = "/layout/create")
    public String createLayout(@Valid Layout layout, BindingResult bn , Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            layout.setCreatedDate(LocalDate.now());

            if(!bn.hasErrors()) {
                layoutRepository.save(layout);

                return "redirect:/layouts";

            } else{
                return "layout/create";
            }
        }else {
            return "login/login";
        }


    }


    @GetMapping(value= "/layout/edit/{id}")
    public String edit(Model model, @PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var layout = layoutRepository.findById(id);

            if(layout.isPresent()){
                model.addAttribute("layout", layout.get());
            }

            return "layout/edit";
        }else {
            return "login/login";
        }


    }



    @PostMapping(value = "/layout/edit/{id}")
    public String edit(@PathVariable int id, @Valid Layout layout, Model model) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            if(layout.getId() == id){
                layoutRepository.update(id,layout.getName(),layout.getDescription());

            }

            return "redirect:/layouts";
        }else {
            return "login/login";
        }


    }

    @GetMapping(value = "/layout/{id}")
    public String details(@PathVariable int id, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var layout = layoutRepository.findById(id);
            var diningTable = diningTableRepository.findAllByLayoutId(layout.get().getId());

            layout.ifPresent(value -> model.addAttribute("layout", value));

            if (!diningTable.isEmpty()) {
                model.addAttribute("diningtables", diningTable);
            }

            return "layout/detail";
        }else {
            return "login/login";
        }



    }

    @GetMapping(value = "/layout/delete/{id}")
    public String delete( Model model,@PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var layout = layoutRepository.findById(id);

            layout.ifPresent(value -> model.addAttribute("layout", value));

            return "layout/delete";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/layout/delete/{id}")
    public String deleteConfirm(@PathVariable int id,  Model model){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            layoutRepository.deleteById(id);

            return "redirect:/layouts";
        }else {
            return "login/login";
        }
    }
}
