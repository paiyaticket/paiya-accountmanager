package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.domains.OrganizationMember;
import events.paiya.accountmanager.domains.projections.OrganizationMemberProjection;
import events.paiya.accountmanager.mappers.projection.OrganizationMemberProjectionMapper;
import events.paiya.accountmanager.repositories.EventOrganizerRepository;
import events.paiya.accountmanager.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventOrganizerServiceImpl implements EventOrganizerService{

    private final EventOrganizerRepository eventOrganizerRepository;
    private final UserRepository userRepository;
    private final OrganizationMemberProjectionMapper organizationMemberProjectionMapper;

    public EventOrganizerServiceImpl(EventOrganizerRepository eventOrganizerRepository,
                                     UserRepository userRepository,
                                     OrganizationMemberProjectionMapper organizationMemberProjectionMapper) {
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.userRepository = userRepository;
        this.organizationMemberProjectionMapper = organizationMemberProjectionMapper;
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
        return eventOrganizerRepository.findById(eventOrganizerId).orElseThrow();
    }

    @Override
    public EventOrganizer findByName(String name) {
        return eventOrganizerRepository.findByName(name).orElseThrow();
    }

    @Override
    public EventOrganizer findByEmail(String email) {
        return eventOrganizerRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public EventOrganizer findByCreatedBy(String userEmail) {
        return eventOrganizerRepository.findByCreatedBy(userEmail).orElseThrow();
    }

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


    //TODO : Ecrire le processus de mise a jour d'un EventOrganiizer
    @Override
    public void updateEventOrganizer(String eventOrganizerId, EventOrganizer eventOrganizer) {

    }

    private List<OrganizationMember> findOrganizationMemberByEmailList(List<String> userEmailList){
        List<OrganizationMemberProjection> organizationMemberProjectionList= userRepository.findUserByEmailIn(userEmailList);
        return organizationMemberProjectionMapper.toEntityList(organizationMemberProjectionList);
    }
}
