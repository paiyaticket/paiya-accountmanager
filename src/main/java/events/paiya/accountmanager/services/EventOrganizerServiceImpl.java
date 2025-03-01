package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.repositories.EventOrganizerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventOrganizerServiceImpl implements EventOrganizerService{

    private final EventOrganizerRepository eventOrganizerRepository;
    public EventOrganizerServiceImpl(EventOrganizerRepository eventOrganizerRepository) {
        this.eventOrganizerRepository = eventOrganizerRepository;
    }

    @Override
    public EventOrganizer create(EventOrganizer eventOrganizer) {
        return eventOrganizerRepository.save(eventOrganizer);
    }

    @Override
    public void delete(String eventOrganizerId) {
        eventOrganizerRepository.deleteById(eventOrganizerId);
    }

    @Override
    public EventOrganizer findById(String eventOrganizerId) {
        Optional<EventOrganizer> opt = eventOrganizerRepository.findById(eventOrganizerId);
        return opt.orElseThrow();
    }

    @Override
    public List<EventOrganizer> findByOrganizationMembersEmail(String email) {
        return eventOrganizerRepository.findByStaffMembers(email);
    }

    @Override
    public List<EventOrganizer> findAll() {
        return eventOrganizerRepository.findAll(Sort.by("name"));
    }

    @Override
    public List<EventOrganizer> findByCreatedBy(String userEmail) {
        return eventOrganizerRepository.findByCreatedBy(userEmail);
    }

    /*
    @Override
    public EventOrganizer addMemberToEventOrganizer(String eventOrganizerId, List<String> userEmailList) {
        List<OrganizationMember> organizationMemberList =  findOrganizationMemberByEmailList(userEmailList);
        EventOrganizer eventOrganizer = this.findById(eventOrganizerId);
        eventOrganizer.addOrganizationMember(organizationMemberList);
        return eventOrganizerRepository.save(eventOrganizer);
    }
    

    @Override
    public EventOrganizer removeMemberFromEventOrganizer(String eventOrganizerId, List<String> userEmailList) {
        List<OrganizationMember> organizationMemberList =  findOrganizationMemberByEmailList(userEmailList);
        EventOrganizer eventOrganizer = this.findById(eventOrganizerId);
        eventOrganizer.removeOrganizationMember(organizationMemberList);
        return eventOrganizerRepository.save(eventOrganizer);
    }

    private List<OrganizationMember> findOrganizationMemberByEmailList(List<String> userEmailList){
        String[] userEmailArray = userEmailList.toArray(new String[0]);
        List<OrganizationMemberProjection> organizationMemberProjectionList= userRepository.findByEmailIn(userEmailArray);
        return organizationMemberProjectionMapper.toEntityList(organizationMemberProjectionList);
    }
    */

    @Override
    public EventOrganizer updateEventOrganizer(EventOrganizer eventOrganizer) {
        return eventOrganizerRepository.save(eventOrganizer);
    }

    @Override
    public void deleteAll() {
        eventOrganizerRepository.deleteAll();
    }

    
}
