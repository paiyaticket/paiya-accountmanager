package events.paiya.accountmanager.domains.projections;

import events.paiya.accountmanager.domains.FinancialAccount;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserFinancialAccount {
    private String id;
    private List<FinancialAccount> financialAccounts;
}
