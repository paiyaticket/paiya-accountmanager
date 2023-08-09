package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.EventOrganizer;

import java.util.List;

public interface EventOrganizerService {

    EventOrganizer create(EventOrganizer eventOrganizer);

    void delete(String eventOrganizerId);

    EventOrganizer findById(String eventOrganizerId);

    EventOrganizer findByName(String name);

    EventOrganizer findByEmail(String email);

    EventOrganizer findByCreatedBy(String userEmail);

    EventOrganizer addMemberToEventOrganizer(String eventOrganizerId, List<String> userEmailList);

    EventOrganizer removeMemberFromEventOrganizer(String eventOrganizerId, List<String> userEmailList);

    void updateEventOrganizer(String eventOrganizerId, EventOrganizer eventOrganizer);
}
