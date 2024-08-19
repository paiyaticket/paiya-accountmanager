package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.CashAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CashAccountRepository extends MongoRepository<CashAccount, String> {

    List<CashAccount> findByOwner(String owner);
    Optional<CashAccount> findByOwnerAndIsDefault(String owner, Boolean isDefault);
    void deleteByOwner(String owner);
    void deleteAllByOwner(String owner);



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
