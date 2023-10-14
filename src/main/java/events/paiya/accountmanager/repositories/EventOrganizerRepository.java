package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.EventOrganizer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventOrganizerRepository  extends MongoRepository<EventOrganizer, String> {

    @Query("{'organizationMembers.email' : ?0}")
    List<EventOrganizer> findByOrganizationMembersEmail(String email);
    List<EventOrganizer> findByCreatedBy(String userEmail);

}
