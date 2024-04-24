package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.entities.Event;
import nbcc.termproject.entities.Reservation;
import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class EventController {

    private final EventRepository eventRepository;
    private final SeatingRepository seatingRepository;
    private final LayoutRepository layoutRepository;
    private final ReservationRepository reservationRepository;
    private final MenuRepository menuRepository;

    private final LoginInfo loginInfo;

    public EventController(EventRepository eventRepository, SeatingRepository seatingRepository, LayoutRepository layoutRepository, ReservationRepository reservationRepository, MenuRepository menuRepository, LoginInfo loginInfo) {
        this.eventRepository = eventRepository;
        this.seatingRepository = seatingRepository;
        this.layoutRepository = layoutRepository;
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
        this.loginInfo = loginInfo;
    }

    @GetMapping("events")
    public String getAllEvents(Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);

            var values = eventRepository.findAll();
            var menus = menuRepository.findAll();
            model.addAttribute("events", values);
            model.addAttribute("menus", menus);
            return "event/index";

        }else{
            return "login/login";
        }



    }

    @GetMapping(value = "/event/create")
    public String create(Model model){
        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);
            model.addAttribute("event", new Event());
            model.addAttribute("layouts", layoutRepository.findAll());
            model.addAttribute("menus", menuRepository.findAll());
            return "event/create";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/event/create")
    public String createEvent(@Valid Event event, BindingResult br, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);

            if(!br.hasErrors()){
                eventRepository.save(event);
                return "redirect:/events";
            }else{
                model.addAttribute("layouts", layoutRepository.findAll());
                model.addAttribute("menus", menuRepository.findAll());
                return "event/create";
            }
        }else {
            return "login/login";
        }

    }

    @GetMapping(value= "/event/edit/{id}")
    public String edit(Model model, @PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);
            var event = eventRepository.findById(id);

            if(event.isPresent()){
                model.addAttribute("event", event.get());
                model.addAttribute("layouts", layoutRepository.findAll());
                model.addAttribute("menus", menuRepository.findAll());
            }

            return "event/edit";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/event/edit/{id}")
    public String editEvent(@PathVariable int id, @Valid Event event, Model model) {

        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);
            eventRepository.updateEvent(id,event.getStartDate(),event.getEndDate(), event.getSeatingDuration(),
                    event.getName(),event.getDescription(), event.getPrice(), event.getMenu().getId());

            return "redirect:/events";
        }else {
            return "login/login";
        }


    }

    @GetMapping(value = "/event/{id}")
    public String details(@PathVariable int id, Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);
            var event = eventRepository.findById(id);
            var seating = seatingRepository.findAllByEventId(event.get().getId());
            var layout = layoutRepository.findAllByEvents_Id(event.get().getId());


            var reservations = new ArrayList<Reservation>();

            for (var seat : seating) {
                var reservationsBySeating = reservationRepository.findAllBySeating(seat);
                reservations.addAll(reservationsBySeating);
            }

            event.ifPresent(value -> model.addAttribute("event", value));


            if (!seating.isEmpty()) {
                model.addAttribute("seatings", seating);
            }

            if(!layout.isEmpty()){
                model.addAttribute("layouts", layout);
            }

            if(!reservations.isEmpty()){
                model.addAttribute("reservations", reservations);
            }

            return "event/detail";
        }else {
            return "login/login";
        }

    }

    @GetMapping(value = "/event/delete/{id}")
    public String delete( Model model,@PathVariable int id){
        var user = loginInfo.getLoggedInUser();

        if(user != null) {
            model.addAttribute(USER_KEY, user);
            var event = eventRepository.findById(id);

            event.ifPresent(value -> model.addAttribute("event", value));

            return "event/delete";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/event/delete/{id}")
    public String deleteEvent(@PathVariable int id,  Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            eventRepository.deleteById(id);
            return "redirect:/events";
        }else {
            return "login/login";
        }


    }

}
