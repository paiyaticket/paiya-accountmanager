package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.repositories.FinancialAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinancialAccountServiceImpl implements FinancialAccountService{

    private final FinancialAccountRepository financialAccountRepository;

    public FinancialAccountServiceImpl(FinancialAccountRepository financialAccountRepository) {
        this.financialAccountRepository = financialAccountRepository;
    }

    @Override
    public FinancialAccount findById(String id) {
        return financialAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public FinancialAccount findByUserIdAndIsDefault(String id, Boolean isDefault) {
        return financialAccountRepository.findByOwnerIdAndIsDefault(id, isDefault).orElseThrow();
    }

    @Override
    public FinancialAccount create(FinancialAccount financialAccount) {
        return financialAccountRepository.insert(financialAccount);
    }

    @Override
    public FinancialAccount update(FinancialAccount financialAccount) {
        return financialAccountRepository.save(financialAccount);
    }

    @Override
    public List<FinancialAccount> findByUserId(String id) {
        return financialAccountRepository.findByOwnerId(id);
    }

    @Override
    public void deleteAllByOwnerId(String id) {
        financialAccountRepository.deleteAllByOwnerId(id);
    }

    @Override
    public void deleteByOwnerId(String id) {
        financialAccountRepository.deleteByOwnerId(id);
    }

    @Override
    public void deleteById(String financialAccountId) {
        financialAccountRepository.deleteById(financialAccountId);
    }

    @Override
    public void deleteAll() {
        financialAccountRepository.deleteAll();
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
