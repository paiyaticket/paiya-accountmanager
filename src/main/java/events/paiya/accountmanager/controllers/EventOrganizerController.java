package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.mappers.EventOrganizerMapper;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import events.paiya.accountmanager.services.EventOrganizerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/event-organizers")
public class EventOrganizerController {

    private final EventOrganizerServiceImpl eventOrganizerService;
    private final EventOrganizerMapper eventOrganizerMapper;

    public EventOrganizerController(EventOrganizerServiceImpl eventOrganizerService, EventOrganizerMapper eventOrganizerMapper) {
        this.eventOrganizerService = eventOrganizerService;
        this.eventOrganizerMapper = eventOrganizerMapper;
    }

    @PostMapping
    public ResponseEntity<EventOrganizerResource> create(@RequestBody EventOrganizerResource eventOrganizerResource){
        EventOrganizer eventOrganizer = eventOrganizerMapper.toEntity(eventOrganizerResource);
        eventOrganizer = eventOrganizerService.create(eventOrganizer);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        eventOrganizerService.delete(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping()
    public ResponseEntity<EventOrganizerResource> findByName(@RequestParam(value = "parameter", defaultValue = "email") String parameter,
                                                             @RequestParam(value = "value") String value) {
        EventOrganizer eventOrganizer = switch (parameter) {
            case "name" -> eventOrganizerService.findByName(value);
            case "userEmail" -> eventOrganizerService.findByCreatedBy(value);
            default -> eventOrganizerService.findByEmail(value);
        };

        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PutMapping("/add-member")
    public ResponseEntity<EventOrganizerResource> addMemberByEoId(@RequestParam(value = "eventOrganizerId") String eventOrganizerId,
                                                                  @RequestBody List<String> organizationMemberList) {
        eventOrganizerService.addMemberToEventOrganizer(eventOrganizerId, organizationMemberList);
        EventOrganizer eventOrganizer = eventOrganizerService.findById(eventOrganizerId);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PutMapping()
    public ResponseEntity<EventOrganizerResource> removeMemberByEoId(@RequestParam(value = "eventOrganizerId") String eventOrganizerId,
                                                                     @RequestBody List<String> organizationMemberList) {
        eventOrganizerService.removeMemberFromEventOrganizer(eventOrganizerId, organizationMemberList);
        EventOrganizer eventOrganizer = eventOrganizerService.findById(eventOrganizerId);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }
}
