package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.FinancialAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAccount {
    private String id;
    private FinancialAccountType financialAccountType;
    private Boolean isDefault;
    private BankAccount bankAccount;
    private CreditCardAccount creditCardAccount;
    private MobileMoneyAccount mobileMoneyAccount;
}
