package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.domains.projections.UserFinancialAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface FinancialAccountRepository extends MongoRepository<User, String> {

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


}
