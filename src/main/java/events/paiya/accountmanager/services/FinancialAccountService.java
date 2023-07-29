package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.FinancialAccount;

import java.util.List;

public interface FinancialAccountService {
    List<FinancialAccount> addFinancialAccountByUserId(String userId, FinancialAccount financialAccount);

    List<FinancialAccount> removeFinancialAccountByUserId(String userId, String financialAccountId);

    FinancialAccount changeDefaultFinancialAccountByUserId(String userId, String financialAccountId);

    FinancialAccount findDefaultFinancialAccountByUserId(String userId);

    List<FinancialAccount> findAllFinancialAccountByUserId(String userId);

}
