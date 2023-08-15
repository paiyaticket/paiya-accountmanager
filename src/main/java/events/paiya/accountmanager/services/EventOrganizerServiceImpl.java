package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.domains.OrganizationMember;
import events.paiya.accountmanager.domains.projections.OrganizationMemberProjection;
import events.paiya.accountmanager.mappers.projection.OrganizationMemberProjectionMapper;
import events.paiya.accountmanager.repositories.EventOrganizerRepository;
import events.paiya.accountmanager.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Optional<EventOrganizer> opt = eventOrganizerRepository.findById(eventOrganizerId);
        return opt.orElseThrow();
    }

    @Override
    public List<EventOrganizer> findByOrganizationMembersEmail(String email) {
        return eventOrganizerRepository.findByOrganizationMembersEmail(email);
    }

    @Override
    public List<EventOrganizer> findByCreatedBy(String userEmail) {
        return eventOrganizerRepository.findByCreatedBy(userEmail);
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

    @Override
    public EventOrganizer updateEventOrganizer(String eventOrganizerId, EventOrganizer eventOrganizer) {
        EventOrganizer eventOrganizerToUpdate = this.findById(eventOrganizerId);
        if (eventOrganizer.getName()!=null && !eventOrganizer.getName().isBlank() && !eventOrganizer.getName().equals(eventOrganizerToUpdate.getName()))
            eventOrganizerToUpdate.setName(eventOrganizer.getName());
        if (eventOrganizer.getEmail()!=null && !eventOrganizer.getEmail().isBlank() && !eventOrganizer.getEmail().equals(eventOrganizerToUpdate.getEmail()))
            eventOrganizerToUpdate.setEmail(eventOrganizer.getEmail());
        if (eventOrganizer.getPhoneNumbers()!=null && !eventOrganizer.getPhoneNumbers().isEmpty() && !eventOrganizer.getPhoneNumbers().equals(eventOrganizerToUpdate.getPhoneNumbers()))
            eventOrganizerToUpdate.setPhoneNumbers(eventOrganizer.getPhoneNumbers());
        if (eventOrganizer.getSocialLinks()!=null && !eventOrganizer.getSocialLinks().isEmpty() && !eventOrganizer.getSocialLinks().equals(eventOrganizerToUpdate.getSocialLinks()))
            eventOrganizerToUpdate.setSocialLinks(eventOrganizer.getSocialLinks());

        return eventOrganizerRepository.save(eventOrganizerToUpdate);
    }

    private List<OrganizationMember> findOrganizationMemberByEmailList(List<String> userEmailList){
        String[] userEmailArray = userEmailList.toArray(new String[0]);
        List<OrganizationMemberProjection> organizationMemberProjectionList= userRepository.findByEmailIn(userEmailArray);
        return organizationMemberProjectionMapper.toEntityList(organizationMemberProjectionList);
    }
}
