package events.paiya.accountmanager.domains;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BankOperationAccount extends FinancialOperationAccount {
    private String banqueCode;
    private String counterNumber;
    private String accountNumber;
    private String ribKey;
    private String iban;
    private String bicSwift;
}
