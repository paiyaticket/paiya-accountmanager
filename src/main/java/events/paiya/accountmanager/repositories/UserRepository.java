package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.domains.projections.OrganizationMemberProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByActiveIsTrue();

    @Query("{'email' : {'$in' : ?0}}")
    List<OrganizationMemberProjection> findByEmailIn(String[] userEmailList);
    Page<User> findAllByActiveIsTrue(Pageable pageable);
    Optional<User> findUserByIdAndActiveIsTrue(String id);
    boolean existsByEmailAndActiveIsTrue(String email);

    @Query("{'_id': ?0}")
    @Update("{'$set' : {'address' : ?1} }")
    void updateUserAddressById(String userId, Address address);

}
