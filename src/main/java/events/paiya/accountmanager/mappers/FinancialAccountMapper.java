package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.resources.FinancialAccountResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FinancialAccountMapper {
    FinancialAccount toEntity(FinancialAccountResource financialAccountResource);
    FinancialAccountResource toResource(FinancialAccount financialAccount);

    List<FinancialAccount> toEntityList(List<FinancialAccountResource> financialAccountResourceList);
    List<FinancialAccountResource> toResourceList(List<FinancialAccount> financialAccountList);
}
