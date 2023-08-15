package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.mappers.EventOrganizerMapper;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import events.paiya.accountmanager.services.EventOrganizerServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<EventOrganizerResource> create(@Valid @RequestBody EventOrganizerResource eventOrganizerResource){
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
    public ResponseEntity<List<EventOrganizerResource>> findByName(@RequestParam(value = "parameter", defaultValue = "createdBy") String parameter,
                                                             @RequestParam(value = "value") String value) {
        List<EventOrganizer> eventOrganizer = ("createdBy".equals(parameter)) ?
                eventOrganizerService.findByCreatedBy(value) : eventOrganizerService.findByOrganizationMembersEmail(value);
        return ResponseEntity.ok(eventOrganizerMapper.toResourceList(eventOrganizer));
    }

    @PutMapping("/add-member")
    public ResponseEntity<EventOrganizerResource> addMemberByEoId(@RequestParam(value = "eventOrganizerId") String eventOrganizerId,
                                                                  @RequestBody @NotNull List<String> organizationMemberList) {
        EventOrganizer eventOrganizer = eventOrganizerService.addMemberToEventOrganizer(eventOrganizerId, organizationMemberList);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PutMapping("/remove-member")
    public ResponseEntity<EventOrganizerResource> removeMemberByEoId(@RequestParam(value = "eventOrganizerId") String eventOrganizerId,
                                                                     @RequestBody List<String> organizationMemberList) {
        EventOrganizer eventOrganizer = eventOrganizerService.removeMemberFromEventOrganizer(eventOrganizerId, organizationMemberList);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventOrganizerResource> update(@PathVariable String id, @RequestBody EventOrganizerResource eventOrganizerResource) {
        EventOrganizer eventOrganizer = eventOrganizerMapper.toEntity(eventOrganizerResource);
        EventOrganizer eventOrganizerUpdated = eventOrganizerService.updateEventOrganizer(id, eventOrganizer);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizerUpdated));
    }
}
