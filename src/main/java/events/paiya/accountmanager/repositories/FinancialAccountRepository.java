package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.FinancialAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialAccountRepository extends MongoRepository<FinancialAccount, String> {

    List<FinancialAccount> findByOwnerId(String id);
    Optional<FinancialAccount> findByOwnerIdAndIsDefault(String id, Boolean isDefault);
    void deleteByOwnerId(String id);
    void deleteAllByOwnerId(String id);



    /*
    @Query("{'_id': ?0}")
    @Update("{'$push' : {'financialAccounts' : ?1} }")
    void addFinancialAccountByUserId(String userId, FinancialAccount financialAccount);

    @Query("{'_id': ?0}")
    @Update("{'$pull' : {'financialAccounts' : { 'id' : {$eq : ?1} } } }")
    void removeFinancialAccountByUserId(String userId, String financialAccountId);

    @Query("{'_id': ?0, 'financialAccounts.isDefault' : true }")
    @Update("{'$set' : {'financialAccounts.$[].isDefault' : false}}")
    void unDefaultFinancialAccountByUserId(String userId);

    @Query("{'_id': ?0, 'financialAccounts.id' : ?1 }")
    @Update("{'$set' : {'financialAccounts.$.isDefault' : true}}")
    void defaultFinancialAccountByUserId(String userId, String financialAccountId);

    @Query("{'_id': ?0, 'financialAccounts.id' : ?1 }")
    FinancialAccount findFinancialAccountById(String userId, String financialAccountId);

    @Query("{'_id': ?0, 'financialAccounts.isDefault' : true }")
    FinancialAccount findDefaultFinancialAccountByUserId(String userId);

    @Query(value = "{'_id': ?0}", fields = "{'financialAccounts': 1}")
    UserFinancialAccount findAllFinancialAccountByUserId(String userId);
    */

}
