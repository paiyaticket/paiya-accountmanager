package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.repositories.CashAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CashAccountServiceImpl implements CashAccountService{

    private final CashAccountRepository cashAccountRepository;

    public CashAccountServiceImpl(CashAccountRepository financialAccountRepository) {
        this.cashAccountRepository = financialAccountRepository;
    }

    @Override
    public CashAccount findById(String id) {
        return cashAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public CashAccount findByOwnerAndIsDefault(String owner, Boolean isDefault) {
        return cashAccountRepository.findByOwnerAndIsDefault(owner, isDefault).orElseThrow();
    }

    @Override
    public CashAccount create(CashAccount financialAccount) {
        return cashAccountRepository.insert(financialAccount);
    }

    @Override
    public CashAccount update(CashAccount financialAccount) {
        return cashAccountRepository.save(financialAccount);
    }

    @Override
    public List<CashAccount> findByOwner(String owner) {
        return cashAccountRepository.findByOwner(owner);
    }

    @Override
    public void deleteAllByOwner(String owner) {
        cashAccountRepository.deleteAllByOwner(owner);
    }

    @Override
    public void deleteByOwner(String owner) {
        cashAccountRepository.deleteByOwner(owner);
    }

    @Override
    public void deleteById(String financialAccountId) {
        cashAccountRepository.deleteById(financialAccountId);
    }

    @Override
    public void deleteAll() {
        cashAccountRepository.deleteAll();
    }

    /*
    @Override
    public List<FinancialAccount> addFinancialAccountByUserId(String userId, FinancialAccount financialAccount) {
        financialAccount.setId(UUID.randomUUID().toString());
        if (this.hasFinancialAccounts(userId))
            this.financialAccountRepository.unDefaultFinancialAccountByUserId(userId);

        this.financialAccountRepository.addFinancialAccountByUserId(userId, financialAccount);
        return this.findAllFinancialAccountByUserId(userId);
    }

    @Override
    public List<FinancialAccount> removeFinancialAccountByUserId(String id, String financialAccountId) {
        this.financialAccountRepository.removeFinancialAccountByUserId(id, financialAccountId);
        return this.userService.findByUserId(id).getFinancialAccounts();
    }

    @Override
    public FinancialAccount changeDefaultFinancialAccountByUserId(String userId, String financialAccountId) {
        if (this.hasFinancialAccounts(userId))
            this.financialAccountRepository.unDefaultFinancialAccountByUserId(userId);
        this.financialAccountRepository.defaultFinancialAccountByUserId(userId, financialAccountId);
        return this.financialAccountRepository.findFinancialAccountById(userId, financialAccountId);
    }

    @Override
    public FinancialAccount findDefaultFinancialAccountByUserId(String userId) {
        return this.financialAccountRepository.findDefaultFinancialAccountByUserId(userId);
    }

    public List<FinancialAccount> findAllFinancialAccountByUserId(String userId) {
        UserFinancialAccount userFinancialAccount = this.financialAccountRepository.findAllFinancialAccountByUserId(userId);
        return userFinancialAccount.getFinancialAccounts();
    }


    private boolean hasFinancialAccounts(String userId){
        List<FinancialAccount> financialAccounts = this.findAllFinancialAccountByUserId(userId);
        return !financialAccounts.isEmpty();
    }
     */
}
