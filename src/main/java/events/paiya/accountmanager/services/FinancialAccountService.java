package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.FinancialAccount;

import java.util.List;

public interface FinancialAccountService {
    FinancialAccount findById(String id);
    FinancialAccount create(FinancialAccount financialAccount);
    FinancialAccount update(FinancialAccount financialAccount);
    List<FinancialAccount> findByUserId(String id);
    FinancialAccount findByUserIdAndIsDefault(String id, Boolean isDefault);
    void deleteAllByOwnerId(String id);
    void deleteByOwnerId(String id);
    void deleteById(String id);
    void deleteAll();
}
