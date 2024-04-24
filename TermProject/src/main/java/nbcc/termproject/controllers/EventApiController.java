package nbcc.termproject.controllers;

import jakarta.validation.Valid;
import nbcc.termproject.dtos.EventDTO;
import nbcc.termproject.entities.Event;
import nbcc.termproject.repositories.EventRepository;
import nbcc.termproject.repositories.LayoutRepository;
import nbcc.termproject.repositories.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static nbcc.termproject.dtos.DTOConverters.*;

@RestController
public class EventApiController {

    private final EventRepository eventRepository;

    private final LayoutRepository layoutRepository;

    private final MenuRepository menuRepository;

    public EventApiController(EventRepository eventRepository, LayoutRepository layoutRepository, MenuRepository menuRepository) {
        this.eventRepository = eventRepository;
        this.layoutRepository = layoutRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/api/events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOs = convertToEventDTOList(events);
        return ResponseEntity.ok(eventDTOs);
    }

    @GetMapping("api/event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable int id) {
        return eventRepository.findById(id)
                .map(event -> ResponseEntity.ok(convertToEventDTO(event)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("api/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(eventDTO);
        }
        Event event = convertFromEventDTO(eventDTO);

        var layout = layoutRepository.findById(eventDTO.getLayoutId());
        var menu = menuRepository.findById(eventDTO.getMenuId());

        event.setLayout(layout.get());
        event.setMenu(menu.get());

        Event savedEvent = eventRepository.save(event);
        EventDTO savedEventDTO = convertToEventDTO(savedEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEventDTO);
    }

    @PutMapping("api/event/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable int id, @RequestBody @Valid EventDTO eventDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(eventDTO);
        }

        return eventRepository.findById(id).map(existingEvent -> {
            Event updatedEvent = convertFromEventDTO(eventDTO);

            var layout = layoutRepository.findById(eventDTO.getLayoutId());
            var menu = menuRepository.findById(eventDTO.getMenuId());

            updatedEvent.setLayout(layout.get());
            updatedEvent.setMenu(menu.get());

            updatedEvent.setId(id);

            eventRepository.save(updatedEvent);
            return ResponseEntity.ok(convertToEventDTO(updatedEvent));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("api/event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        try {
            eventRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
