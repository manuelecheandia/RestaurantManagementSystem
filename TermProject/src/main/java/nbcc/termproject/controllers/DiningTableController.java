package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.DiningTable;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.LayoutRepository;
import nbcc.termproject.repositories.DiningTableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class DiningTableController {

    private final LayoutRepository layoutRepository;
    private final DiningTableRepository diningTableRepository;

    private  final LoginInfo loginInfo;

    public DiningTableController(LayoutRepository layoutRepository, DiningTableRepository diningTableRepository, LoginInfo loginInfo) {
        this.layoutRepository = layoutRepository;
        this.diningTableRepository = diningTableRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping("/diningtable/create/{id}")
    public String create(Model model,  @PathVariable int id){

        var user = loginInfo.getLoggedInUser();
        var layout = layoutRepository.findById(id);
        if(user != null){
            model.addAttribute(USER_KEY, user);

            model.addAttribute("diningtable", new DiningTable());
            model.addAttribute("layout", layout.get());
            return "diningtable/create";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value= "/diningtable/create/{id}")
    public String createTable(@Valid DiningTable diningTable, BindingResult br, @PathVariable int id, Model model) {

        var user = loginInfo.getLoggedInUser();
        if(user != null){
            model.addAttribute(USER_KEY, user);
            var layout = layoutRepository.findById(id);

            DiningTable newDinTab = new DiningTable();
            newDinTab.setNumberOfSeats(diningTable.getNumberOfSeats());

            if(!br.hasErrors()){
                newDinTab.setLayout(layout.get());
                diningTableRepository.save(newDinTab);
                return "redirect:/layout/" + id;
            } else {

                model.addAttribute("layout", layout.get());

                return "diningtable/create";
            }


        }else{
            return "login/login";
        }


    }

    @GetMapping(value= "/diningtable/edit/{id}")
    public String edit(Model model, @PathVariable int id){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var diningTable = diningTableRepository.findById(id);
            if(diningTable.isPresent()){

                model.addAttribute("diningtable", diningTable.get());
            }

            return "diningtable/edit";

        }else{
            return "login/login";
        }


    }

    @PostMapping(value = "/diningtable/edit/{id}")
    public String edit(@PathVariable int id, @Valid DiningTable diningTable, Model model) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            if(diningTable.getId() == id){
                diningTableRepository.update(id,diningTable.getNumberOfSeats());

            }

            return "redirect:/layouts";

        }else{
            return "login/login";
        }

    }

    @GetMapping(value = "/diningtable/delete/{id}")
    public String delete( Model model,@PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            var diningTable = diningTableRepository.findById(id);

            diningTable.ifPresent(value -> model.addAttribute("diningtable", value));

            return "diningtable/delete";

        }else{
            return "login/login";
        }



    }

    @PostMapping(value = "/diningtable/delete/{id}")
    public String deleteConfirm(@PathVariable int id,  Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            diningTableRepository.deleteById(id);

            return "redirect:/layouts";

        }else{
            return "login/login";
        }

    }

}
