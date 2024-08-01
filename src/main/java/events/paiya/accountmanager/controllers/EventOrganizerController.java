package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.mappers.EventOrganizerMapper;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import events.paiya.accountmanager.resources.MemberBundleResource;
import events.paiya.accountmanager.services.EventOrganizerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/event-organizers")
public class EventOrganizerController {

    private final EventOrganizerServiceImpl eventOrganizerService;
    private final EventOrganizerMapper eventOrganizerMapper;

    public EventOrganizerController(EventOrganizerServiceImpl eventOrganizerService, EventOrganizerMapper eventOrganizerMapper) {
        this.eventOrganizerService = eventOrganizerService;
        this.eventOrganizerMapper = eventOrganizerMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventOrganizerResource> findById(@PathVariable(name = "id") String id) {
        EventOrganizer eventOrganizer = eventOrganizerService.findById(id);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @GetMapping()
    public ResponseEntity<List<EventOrganizerResource>> findEventOrganizerCreatedBy(@RequestParam(name = "email") String userEmail) {
        List<EventOrganizer> eventOrganizerList = eventOrganizerService.findByCreatedBy(userEmail);
        return ResponseEntity.ok(eventOrganizerMapper.toResourceList(eventOrganizerList));
    }
    
    
    @PostMapping
    public ResponseEntity<EventOrganizerResource> create(@Valid @RequestBody EventOrganizerResource eventOrganizerResource){
        EventOrganizer eventOrganizer = eventOrganizerMapper.toEntity(eventOrganizerResource);
        eventOrganizer = eventOrganizerService.create(eventOrganizer);
        URI uri = URI.create("/v1/event-organizers/"+eventOrganizer.getId());
        return ResponseEntity.created(uri).body(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @GetMapping("/criteria")
    public ResponseEntity<List<EventOrganizerResource>> findByName(@RequestParam(value = "parameter", defaultValue = "owner") String parameter,
                                                                   @RequestParam(value = "value") String value) {
        List<EventOrganizer> eventOrganizer = ("owner".equals(parameter)) ?
                eventOrganizerService.findByCreatedBy(value) : eventOrganizerService.findByOrganizationMembersEmail(value);
        return ResponseEntity.ok(eventOrganizerMapper.toResourceList(eventOrganizer));
    }

    @PatchMapping("/add-members")
    public ResponseEntity<EventOrganizerResource> addMemberByEoId(@RequestBody MemberBundleResource memberBundleResource) {
        EventOrganizer eventOrganizer = eventOrganizerService.addMemberToEventOrganizer(memberBundleResource.getEventOrganizerId(), memberBundleResource.getMemberEmailList());
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PatchMapping("/remove-members")
    public ResponseEntity<EventOrganizerResource> removeMemberByEoId(@RequestBody MemberBundleResource memberBundleResource) {
        EventOrganizer eventOrganizer = eventOrganizerService.removeMemberFromEventOrganizer(memberBundleResource.getEventOrganizerId(), memberBundleResource.getMemberEmailList());
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventOrganizerResource> update(@PathVariable String id, @RequestBody EventOrganizerResource eventOrganizerResource) {
        EventOrganizer eventOrganizer = eventOrganizerService.findById(id);
        eventOrganizerMapper.updateFromResource(eventOrganizerResource, eventOrganizer);
        eventOrganizer = eventOrganizerService.updateEventOrganizer(eventOrganizer);
        return ResponseEntity.ok(eventOrganizerMapper.toResource(eventOrganizer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        eventOrganizerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
