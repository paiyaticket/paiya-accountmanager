package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.BankAccount;
import events.paiya.accountmanager.domains.CardAccount;
import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.domains.DigitalWalletAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.resources.BankAccountResource;
import events.paiya.accountmanager.resources.CardAccountResource;
import events.paiya.accountmanager.resources.CashAccountResource;
import events.paiya.accountmanager.resources.DigitalWalletAccountResource;
import events.paiya.accountmanager.resources.MobileMoneyAccountResource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CashAccountMapper {
    @SubclassMapping(source = BankAccountResource.class, target = BankAccount.class)
    @SubclassMapping(source = CardAccountResource.class, target = CardAccount.class)
    @SubclassMapping(source = MobileMoneyAccountResource.class, target = MobileMoneyAccount.class)
    @SubclassMapping(source = DigitalWalletAccountResource.class, target = DigitalWalletAccount.class)
    CashAccount toEntity(CashAccountResource cashAccountResource);

    @SubclassMapping(source = BankAccount.class, target = BankAccountResource.class)
    @SubclassMapping(source = CardAccount.class, target = CardAccountResource.class)
    @SubclassMapping(source = MobileMoneyAccount.class, target = MobileMoneyAccountResource.class)
    @SubclassMapping(source = DigitalWalletAccount.class, target = DigitalWalletAccountResource.class)
    CashAccountResource toResource(CashAccount cashAccount);

    List<CashAccount> toEntityList(List<CashAccountResource> cashAccountResourceList);
    List<CashAccountResource> toResourceList(List<CashAccount> fcashAccountList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBankAccountFromResource(BankAccountResource resource, @MappingTarget BankAccount account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardAccountFromResource(CardAccountResource resource, @MappingTarget CardAccount account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMobileMoneyAccountFromResource(MobileMoneyAccountResource resource, @MappingTarget MobileMoneyAccount account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDigitalWalletAccountFromResource(DigitalWalletAccountResource resource, @MappingTarget DigitalWalletAccount account);

}
