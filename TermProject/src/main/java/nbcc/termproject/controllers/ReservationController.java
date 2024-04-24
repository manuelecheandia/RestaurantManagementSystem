package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.config.EmailConfig;
import nbcc.termproject.entities.Event;
import nbcc.termproject.entities.Reservation;

import nbcc.termproject.login.LoginInfo;
import nbcc.termproject.repositories.*;
import nbcc.termproject.services.EmailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

import static nbcc.termproject.constants.AttributeConstants.USER_KEY;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    private final EventRepository eventRepository;

    private final SeatingRepository seatingRepository;

    private final LayoutRepository layoutRepository;

    private final DiningTableRepository diningTableRepository;

    private final EmailConfig emailConfig;

    private final EmailSender emailSender;

    private  final LoginInfo loginInfo;


    public ReservationController(ReservationRepository reservationRepository, EventRepository eventRepository, SeatingRepository seatingRepository, LayoutRepository layoutRepository, DiningTableRepository diningTableRepository, EmailConfig emailConfig, EmailSender emailSender, LoginInfo loginInfo) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
        this.seatingRepository = seatingRepository;
        this.layoutRepository = layoutRepository;
        this.diningTableRepository = diningTableRepository;
        this.emailConfig = emailConfig;
        this.emailSender = emailSender;
        this.loginInfo = loginInfo;
    }

    @GetMapping("/reservation/create/{id}")
    public String create(Model model, @PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);

            // var eventFromSeating = seatingRepository.selectEventBySeatingId(id);

            var seating = seatingRepository.findById(id);


            //var event = eventRepository.findById(eventid);

            model.addAttribute("reservation", new Reservation());
            model.addAttribute("seating", seating.get());
            return "reservation/create";

        }else {
            return "login/login";
        }


    }



    @PostMapping(value = "/reservation/create/{id}")
    public String create(@Valid Reservation reservation, BindingResult br ,  @PathVariable int id,Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var seating = seatingRepository.findById(id);
            var event = eventRepository.findById(seating.get().getEvent().getId());
            reservation.setId(0);
            String defaultStatus = "pending";

            if(!br.hasErrors()){
                reservation.setSeating(seating.get());
                reservation.setStatus(defaultStatus);
                reservationRepository.save(reservation);
                sendReservationEmail(reservation, event, "Create");
                return "redirect:/event/"+ event.get().getId();
            } else {
                model.addAttribute("seating", seating);


                return "reservation/create";
            }
        }else {
            return "login/login";
        }

    }

    @GetMapping("/reservations/{id}")
    public String getReservationsBySeating(Model model, @PathVariable int id, @RequestParam(required = false) String status) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var event = eventRepository.findById(id);
            var seatings = seatingRepository.findAllByEventId(id);
            var reservations = new ArrayList<Reservation>();

            for (var seating : seatings) {
                var reservationsBySeating = reservationRepository.findAllBySeating(seating);
                reservations.addAll(reservationsBySeating);
            }

            sortAndFilter(model, status, reservations);
            model.addAttribute("event", event.get());
            return "reservation/ByEvent";
        }else {
            return "login/login";
        }


    }

    @GetMapping("/reservations")
    public String getReservations(Model model, @RequestParam(name = "event", required = false) Integer  eventId, @RequestParam(required = false) String status) {

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var events = eventRepository.findAll();

            List<Reservation> reservations = reservationRepository.findAll();
            List<Reservation> filteredReservations = new ArrayList<>();

            if (eventId != null) {
                // Filter by event
                reservations = reservations.stream()
                        .filter(reservation -> reservation.getSeating().getEvent().getId() == (eventId))
                        .collect(Collectors.toList());
            }

            if (status != null && !status.isEmpty()) {
                // Filter by status
                filteredReservations = reservations.stream()
                        .filter(reservation -> reservation.getStatus().equals(status))
                        .collect(Collectors.toList());
            } else {
                filteredReservations.addAll(reservations);
            }

            // Sort the filtered reservations
            filteredReservations.sort(Comparator.comparing(reservation -> reservation.getSeating().getEvent().getStartDate()));

            model.addAttribute("reservations", filteredReservations);
            model.addAttribute("events", events);
            return "reservation/index";
        }else {
            return "login/login";
        }

    }

    private void sortAndFilter(Model model, @RequestParam(required = false) String status, List<Reservation> reservations) {

        List<Reservation> filteredReservations;
        if (status != null && !status.isEmpty()) {
            filteredReservations = reservations.stream()
                    .filter(reservation -> reservation.getStatus().equals(status))
                    .collect(Collectors.toList());
        } else {
            filteredReservations = new ArrayList<>(reservations);
        }

        filteredReservations.sort(Comparator.comparing(reservation -> reservation.getSeating().getStartDateAndTime()));

        model.addAttribute("reservations", filteredReservations);
    }

    @GetMapping("/reservation/edit/{id}")
    public String edit(Model model, @PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            // var eventFromSeating = seatingRepository.selectEventBySeatingId(id);

            var reservation = reservationRepository.findById(id);
            var seating = seatingRepository.findById(reservation.get().getSeating().getId());

            var event = eventRepository.findById(seating.get().getEvent().getId());

            var layout = layoutRepository.findById(event.get().getLayout().getId());

            var table = diningTableRepository.findAllByLayoutId(layout.get().getId());
            model.addAttribute("table", table);

            model.addAttribute("reservation", reservation.get());
            model.addAttribute("seating", reservation.get().getSeating());

            return "reservation/edit";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/reservation/edit/{id}")
    public String edit(@Valid Reservation reservation, BindingResult br ,  @PathVariable int id,Model model){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var seating = seatingRepository.findById(reservation.getSeating().getId());
            var event = eventRepository.findById(seating.get().getEvent().getId());
            var layout = layoutRepository.findById(event.get().getLayout().getId());
            var table = diningTableRepository.findAllByLayoutId(layout.get().getId());


            if(!br.hasErrors()){
                reservation.setSeating(seating.get());

                if (reservation.getDiningTable() != null && !reservation.getStatus().equals("approved")) {
                    String errorMessage = "Cannot select a table if reservation is not approved.";

                    model.addAttribute("errorMessage", errorMessage);
                    model.addAttribute("table", table);
                    return "redirect:/reservation/edit/{id}";
                }else{

                    reservationRepository.save(reservation);
                    sendReservationEmail(reservation, event, "Edit");
                    return "redirect:/reservations";}
            } else {
                model.addAttribute("seating", seating);
                model.addAttribute("table", table);

                return "reservation/edit";
            }
        }else {
            return "login/login";
        }



    }

    @GetMapping(value = "/reservation/delete/{id}")
    public String delete( Model model,@PathVariable int id){

        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            var reservation = reservationRepository.findById(id);

            reservation.ifPresent(value -> model.addAttribute("reservation", value));

            return "reservation/delete";
        }else {
            return "login/login";
        }


    }

    @PostMapping(value = "/reservation/delete/{id}")
    public String deleteEvent(@PathVariable int id,  Model model){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            reservationRepository.deleteById(id);
            return "redirect:/reservations";
        }else {
            return "login/login";
        }


    }

    @GetMapping("/reservation/{id}")
    public String detail(Model model, @PathVariable int id){
        var user = loginInfo.getLoggedInUser();

        if(user != null){
            model.addAttribute(USER_KEY, user);
            reservationRepository.deleteById(id);
            return "redirect:/reservations";
        }else {
            return "login/login";
        }
    }



    private void sendReservationEmail(Reservation reservation, Optional<Event> event, String tipo) {
        try{

            if(reservation.getEmailAddress() != null && !reservation.getEmailAddress().isBlank()) {
                var subject = "";
                String message = "";
                if(tipo.equals("Create")){
                    subject = "Reservation requested";
                    message = ", reservation request was successful: ";
                }else{
                    subject = "Reservation Status changed";
                    message = ", reservation status was changed: ";
                }



                var text = "Hello " + reservation.getFirstName() + message+"\n" +
                        "Event : " + event.get().getName() + "\n"+
                        "Group Size: " + reservation.getGroupSize()+ "\n"+
                        "Status: " + reservation.getStatus();

                emailSender.send(subject, text, emailConfig.getDefaultFromEmailAddress(), reservation.getEmailAddress());
            }else {
                System.out.println("Can't send email for user "+ reservation.getFirstName() + ", no email was provided");
            }

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }



}
