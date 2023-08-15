package events.paiya.accountmanager.domains;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserFinancialAccount {
    private String id;
    private List<FinancialAccount> financialAccounts;
}
