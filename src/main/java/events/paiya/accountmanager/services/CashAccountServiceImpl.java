package events.paiya.accountmanager.services;

import java.util.List;

import org.springframework.stereotype.Component;

import events.paiya.accountmanager.domains.BankAccount;
import events.paiya.accountmanager.domains.CardAccount;
import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.domains.DigitalWalletAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.mappers.CashAccountMapper;
import events.paiya.accountmanager.repositories.CashAccountRepository;
import events.paiya.accountmanager.resources.BankAccountResource;
import events.paiya.accountmanager.resources.CardAccountResource;
import events.paiya.accountmanager.resources.CashAccountResource;
import events.paiya.accountmanager.resources.DigitalWalletAccountResource;
import events.paiya.accountmanager.resources.MobileMoneyAccountResource;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CashAccountServiceImpl implements CashAccountService{

    private final CashAccountRepository cashAccountRepository;
    private final CashAccountMapper cashAccountMapper;

    public CashAccountServiceImpl(CashAccountRepository financialAccountRepository, CashAccountMapper cashAccountMapper) {
        this.cashAccountRepository = financialAccountRepository;
        this.cashAccountMapper = cashAccountMapper;
    }

    @Override
    public CashAccount findById(String id) {
        return cashAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public CashAccount findByOwnerAndIsDefault(String owner, Boolean isDefault) {
        return cashAccountRepository.findByOwnerAndIsDefault(owner, isDefault).orElse(null);
    }

    @Override
    public CashAccount create(CashAccount financialAccount) {
        return cashAccountRepository.insert(financialAccount);
    }

    @Override
    public CashAccount update(CashAccount cashAccount) {
        return cashAccountRepository.save(cashAccount);
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

    public CashAccount updateCashAccountFromResource(CashAccountResource cashAccountResource, CashAccount cashAccount){
        switch (cashAccountResource.getFinancialAccountType()) {
            case BANK_ACCOUNT:
                BankAccountResource bankAccountResource = (BankAccountResource) cashAccountResource;
                BankAccount bankAccount = (BankAccount) cashAccount;
                cashAccountMapper.updateBankAccountFromResource(bankAccountResource , bankAccount);
                break;

            case CARD:
                CardAccountResource cardAccountResource = (CardAccountResource) cashAccountResource;
                CardAccount cardAccount = (CardAccount) cashAccount;
                cashAccountMapper.updateCardAccountFromResource(cardAccountResource, cardAccount);
                break;

            case MOBILE_MONEY:
                MobileMoneyAccountResource mobileMoneyAccountResource = (MobileMoneyAccountResource) cashAccountResource;
                MobileMoneyAccount mobileMoneyAccount = (MobileMoneyAccount) cashAccount;
                cashAccountMapper.updateMobileMoneyAccountFromResource(mobileMoneyAccountResource, mobileMoneyAccount);
                break;
        
            default:
                DigitalWalletAccountResource digitalWalletAccountResource = (DigitalWalletAccountResource) cashAccountResource;
                DigitalWalletAccount digitalWalletAccount = (DigitalWalletAccount) cashAccount;
                cashAccountMapper.updateDigitalWalletAccountFromResource(digitalWalletAccountResource, digitalWalletAccount);
                break;
        }

        return cashAccount;
    }
}
