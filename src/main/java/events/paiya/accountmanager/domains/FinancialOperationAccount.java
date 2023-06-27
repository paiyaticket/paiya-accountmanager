package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.FinancialOperationAccountType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class FinancialOperationAccount {
    @Id
    private String id;
    private FinancialOperationAccountType financialOperationAccountType;
    private Boolean isDefault;
}
