package events.paiya.accountmanager.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import events.paiya.accountmanager.domains.BankAccount;
import events.paiya.accountmanager.domains.CreditCardAccount;
import events.paiya.accountmanager.domains.MobileMoneyAccount;
import events.paiya.accountmanager.enumerations.FinancialAccountType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialAccountResource extends BaseResource{
    private String id;
    private FinancialAccountType financialAccountType;
    private Boolean isDefault;
    private BankAccount bankAccount;
    private CreditCardAccount creditCardAccount;
    private MobileMoneyAccount mobileMoneyAccount;
}
