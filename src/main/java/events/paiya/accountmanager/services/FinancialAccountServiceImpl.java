package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.domains.projections.UserFinancialAccount;
import events.paiya.accountmanager.repositories.FinancialAccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class FinancialAccountServiceImpl implements FinancialAccountService{

    private final FinancialAccountRepository financialAccountRepository;
    private final UserServiceImpl userService;

    public FinancialAccountServiceImpl(FinancialAccountRepository financialAccountRepository,
                                       UserServiceImpl userService) {
        this.financialAccountRepository = financialAccountRepository;
        this.userService = userService;
    }

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
}
