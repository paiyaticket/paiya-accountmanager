package events.paiya.accountmanager.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BankAccount extends FinancialAccount{
    private String banqueCode;
    private String counterNumber;
    private String accountNumber;
    private String ribKey;
    private String iban;
    private String bicSwift;
}
