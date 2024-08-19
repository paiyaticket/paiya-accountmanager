package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.EventOrganizer;

import java.util.List;

public interface EventOrganizerService {

    EventOrganizer create(EventOrganizer eventOrganizer);

    void delete(String eventOrganizerId);

    EventOrganizer findById(String eventOrganizerId);

    List<EventOrganizer> findByOrganizationMembersEmail(String email);

    List<EventOrganizer> findAll();

    List<EventOrganizer> findByCreatedBy(String userEmail);

    // EventOrganizer addMemberToEventOrganizer(String eventOrganizerId, List<String> organizationMemberList);

    // EventOrganizer removeMemberFromEventOrganizer(String eventOrganizerId, List<String> userEmailList);

    EventOrganizer updateEventOrganizer(EventOrganizer eventOrganizer);

    void deleteAll();
}
