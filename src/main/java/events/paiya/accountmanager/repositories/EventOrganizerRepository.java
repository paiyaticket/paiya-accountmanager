package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.domains.OrganizationMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface EventOrganizerRepository  extends MongoRepository<EventOrganizer, String> {
    Optional<EventOrganizer> findByName(String name);

    Optional<EventOrganizer> findByEmail(String email);

    Optional<EventOrganizer> findByCreatedBy(String userEmail);

    @Query("{'_id': ?0}")
    @Update("{'$push' : {'organizationMembers' : ?1} }")
    void addMemberByEoId(String eventOrganizerId, OrganizationMember organizationMember);

    @Query("{'_id': ?0}")
    @Update("{'$pull' : {'organizationMembers' : { 'email' : {$eq : ?1} } } }")
    void removeMemberByEoId(String eventOrganizerId, String email);

    @Query("{'_id': ?0}")
    @Update("{'$set' : {'address' : ?1} }")
    void updateEventOrganizerAddressById(String eventOrganizerId, Address address);
}
