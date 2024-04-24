package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.Seating;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.EventRepository;
import nbcc.termproject.repositories.SeatingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeatingController {
    private final EventRepository eventRepository;

    private final SeatingRepository seatingRepository;

    private  final LoginInfo loginInfo;

    public SeatingController(EventRepository eventRepository, SeatingRepository seatingRepository, LoginInfo loginInfo) {
        this.eventRepository = eventRepository;
        this.seatingRepository = seatingRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping("/seating/create/{id}")
    public String create(Model model, @PathVariable int id){

        var event = eventRepository.findById(id);

        model.addAttribute("seating", new Seating());
        model.addAttribute("event", event.get());
        return "seating/create";
    }

    @PostMapping(value= "/seating/create/{id}")
    public String createSeating(@Valid Seating seating, BindingResult br, @PathVariable int id, Model model) {


        var event = eventRepository.findById(id);

        Seating newSeating = new Seating();
        newSeating.setStartDateAndTime(seating.getStartDateAndTime());

        if(!br.hasErrors()){
            newSeating.setEvent(event.get());
            seatingRepository.save(newSeating);
            return "redirect:/event/" + id;
        } else {

            model.addAttribute("event", event.get());

            return "seating/create";
        }

    }

    @GetMapping(value= "/seating/edit/{id}")
    public String edit(Model model, @PathVariable int id){


        var seating = seatingRepository.findById(id);

        if(seating.isPresent()){
            model.addAttribute("seating", seating.get());
        }

        return "seating/edit";
    }

    @PostMapping(value = "/seating/edit/{id}")
    public String edit(@PathVariable int id, @Valid Seating seating, Model model) {


        if(seating.getId() == id){
            seatingRepository.update(id,seating.getStartDateAndTime());

        }
        var eventId = seatingRepository.findById(id).get().getEvent().getId();

        return "redirect:/event/" + eventId;
    }

    @GetMapping(value = "/seating/delete/{id}")
    public String delete( Model model,@PathVariable int id){
        var seating = seatingRepository.findById(id);

        seating.ifPresent(value -> model.addAttribute("seating", value));

        return "seating/delete";
    }

    @PostMapping(value = "/seating/delete/{id}")
    public String deleteConfirm(@PathVariable int id,  Model model){
        seatingRepository.deleteById(id);

        return "redirect:/events";
    }
}
