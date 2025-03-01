package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.enumerations.MobileMoneyProvider;
import events.paiya.accountmanager.repositories.CashAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CashAccountServiceImplTest {

    private final String ID = UUID.randomUUID().toString();

    @Mock
    private CashAccountRepository cashAccountRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private CashAccountServiceImpl cashAccountService;

    @Test()
    @DisplayName(value = "Given Id, When Id exist, Then return CashAccount")
    void findById(){

        Mockito.when(cashAccountRepository.findById(ID)).thenReturn(Optional.of(this.buildCashAccount()));

        CashAccount account = cashAccountService.findById(ID);

        Assert.notNull(account, () -> "The [account] object must be null");
        Mockito.verify(cashAccountRepository, Mockito.times(1)).findById(ID);
    }

    @Test()
    @DisplayName(value = "Given Id, When Id not exist, Then throw exception")
    void findById_ThrowsNoSuchElement(){

        Mockito.when(cashAccountRepository.findById(ID)).thenThrow(new NoSuchElementException());

        Assertions.assertThrows(NoSuchElementException.class, () -> cashAccountService.findById(ID));
        Mockito.verify(cashAccountRepository, Mockito.times(1)).findById(ID);
    }

    private CashAccount buildCashAccount(){
        return MobileMoneyAccount.builder().id(ID).mobileMoneyProvider(MobileMoneyProvider.WAVE_CI).build();
    }

    /*
    @Test
    void givenUserIdAndFAccount_whenAdd_thenReturnFinantialAccountList(){
        UserFinancialAccount userFinancialAccount = UserFinancialAccount.builder()
                            .id(UUID.randomUUID().toString()).financialAccounts(new ArrayList<>()).build();

        Mockito.when(financialAccountRepository.findAllFinancialAccountByUserId(USER_ID)).thenReturn(userFinancialAccount);
        Mockito.doNothing().when(financialAccountRepository).addFinancialAccountByUserId(Mockito.anyString(), Mockito.any(FinancialAccount.class));

        financialAccountService.addFinancialAccountByUserId(USER_ID, buildFinancialAccount());

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .addFinancialAccountByUserId(Mockito.anyString(), Mockito.any(FinancialAccount.class));
    }

    @Test
    void givenUserIdAndFAccount_whenHasFinancialAccounts_thenUndefaultBeforeReturnFinantialAccountList(){
        UserFinancialAccount userFinancialAccount = UserFinancialAccount.builder()
                .id(UUID.randomUUID().toString())
                .financialAccounts(List.of(FinancialAccount.builder().build())).build();

        Mockito.when(financialAccountRepository.findAllFinancialAccountByUserId(USER_ID)).thenReturn(userFinancialAccount);
        Mockito.doNothing().when(financialAccountRepository).addFinancialAccountByUserId(Mockito.anyString(), Mockito.any(FinancialAccount.class));

        financialAccountService.addFinancialAccountByUserId(USER_ID, buildFinancialAccount());

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .unDefaultFinancialAccountByUserId(Mockito.anyString());
    }

    @Test
    void removeFinancialAccountByUserId(){
        Mockito.doNothing().when(financialAccountRepository).removeFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.when(userService.findByUserId(Mockito.anyString())).thenReturn(User.builder().build());

        financialAccountService.removeFinancialAccountByUserId(USER_ID, USER_ID);

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .removeFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(userService, Mockito.times(1)).findByUserId(Mockito.anyString());
    }

    @Test
    void givenUserIdAndFAccount_whenHasFinancialAccounts_thenChangeDefaultFinancialAccountByUserId(){
        UserFinancialAccount userFinancialAccount = UserFinancialAccount.builder()
                .id(UUID.randomUUID().toString()).financialAccounts(new ArrayList<>()).build();

        Mockito.when(financialAccountRepository.findAllFinancialAccountByUserId(USER_ID)).thenReturn(userFinancialAccount);
        Mockito.doNothing().when(financialAccountRepository).defaultFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.when(financialAccountRepository.findFinancialAccountById(Mockito.anyString(), Mockito.anyString())).thenReturn(this.buildFinancialAccount());

        financialAccountService.changeDefaultFinancialAccountByUserId(USER_ID, USER_ID);

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .defaultFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .findFinancialAccountById(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    void givenUserIdAndFAccount_whenHasFinancialAccounts_thenUndefaultBeforeChangeDefaultFinancialAccountByUserId(){
        UserFinancialAccount userFinancialAccount = UserFinancialAccount.builder()
                .id(UUID.randomUUID().toString())
                .financialAccounts(List.of(FinancialAccount.builder().build())).build();

        Mockito.when(financialAccountRepository.findAllFinancialAccountByUserId(USER_ID)).thenReturn(userFinancialAccount);
        Mockito.doNothing().when(financialAccountRepository).unDefaultFinancialAccountByUserId(Mockito.anyString());
        Mockito.doNothing().when(financialAccountRepository).defaultFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.when(financialAccountRepository.findFinancialAccountById(Mockito.anyString(), Mockito.anyString())).thenReturn(this.buildFinancialAccount());

        financialAccountService.changeDefaultFinancialAccountByUserId(USER_ID, USER_ID);

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .unDefaultFinancialAccountByUserId(Mockito.anyString());
        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .defaultFinancialAccountByUserId(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .findFinancialAccountById(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    void findDefaultFinancialAccountByUserId(){
        Mockito.when(financialAccountRepository.findDefaultFinancialAccountByUserId(Mockito.anyString()))
                .thenReturn(this.buildFinancialAccount());

        FinancialAccount fa = financialAccountService.findDefaultFinancialAccountByUserId(USER_ID);

        Mockito.verify(financialAccountRepository, Mockito.times(1))
                .findDefaultFinancialAccountByUserId(Mockito.anyString());
        Assertions.assertNotNull(fa);
    }

    private FinancialAccount buildFinancialAccount(){
        return FinancialAccount.builder().id(UUID.randomUUID().toString())
                .mobileMoneyAccount(new MobileMoneyAccount("+225", "0745424117", MobileMoneyProvider.WAVE_CI))
                .build();
    }

     */
}