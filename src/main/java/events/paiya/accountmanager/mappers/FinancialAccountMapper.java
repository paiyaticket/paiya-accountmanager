package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.BankAccount;
import events.paiya.accountmanager.domains.CardAccount;
import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.resources.BankAccountResource;
import events.paiya.accountmanager.resources.CardAccountResource;
import events.paiya.accountmanager.resources.FinancialAccountResource;
import events.paiya.accountmanager.resources.MobileMoneyAccountResource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface FinancialAccountMapper {
    @SubclassMapping(source = BankAccountResource.class, target = BankAccount.class)
    @SubclassMapping(source = CardAccountResource.class, target = CardAccount.class)
    @SubclassMapping(source = MobileMoneyAccountResource.class, target = MobileMoneyAccount.class)
    FinancialAccount toEntity(FinancialAccountResource financialAccountResource);

    @SubclassMapping(source = BankAccount.class, target = BankAccountResource.class)
    @SubclassMapping(source = CardAccount.class, target = CardAccountResource.class)
    @SubclassMapping(source = MobileMoneyAccount.class, target = MobileMoneyAccountResource.class)
    FinancialAccountResource toResource(FinancialAccount financialAccount);

    List<FinancialAccount> toEntityList(List<FinancialAccountResource> financialAccountResourceList);
    List<FinancialAccountResource> toResourceList(List<FinancialAccount> financialAccountList);

    void updateFinancialAccoutFromResource(FinancialAccountResource resource, @MappingTarget FinancialAccount account);
}
