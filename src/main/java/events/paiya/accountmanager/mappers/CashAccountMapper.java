package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.BankAccount;
import events.paiya.accountmanager.domains.CardAccount;
import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.resources.BankAccountResource;
import events.paiya.accountmanager.resources.CardAccountResource;
import events.paiya.accountmanager.resources.CashAccountResource;
import events.paiya.accountmanager.resources.MobileMoneyAccountResource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface CashAccountMapper {
    @SubclassMapping(source = BankAccountResource.class, target = BankAccount.class)
    @SubclassMapping(source = CardAccountResource.class, target = CardAccount.class)
    @SubclassMapping(source = MobileMoneyAccountResource.class, target = MobileMoneyAccount.class)
    CashAccount toEntity(CashAccountResource cashAccountResource);

    @SubclassMapping(source = BankAccount.class, target = BankAccountResource.class)
    @SubclassMapping(source = CardAccount.class, target = CardAccountResource.class)
    @SubclassMapping(source = MobileMoneyAccount.class, target = MobileMoneyAccountResource.class)
    CashAccountResource toResource(CashAccount cashAccount);

    List<CashAccount> toEntityList(List<CashAccountResource> cashAccountResourceList);
    List<CashAccountResource> toResourceList(List<CashAccount> fcashAccountList);

    void updateCashAccountFromResource(CashAccountResource resource, @MappingTarget CashAccount account);
}
