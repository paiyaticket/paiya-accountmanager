package events.paiya.accountmanager.domain;

import events.paiya.accountmanager.enumeration.FinancialAccountType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class FinancialAccount {
    @Id
    private String id;
    private FinancialAccountType financialAccountType;
    private Boolean isDefault;
}
