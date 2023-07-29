package events.paiya.accountmanager.domains;

import lombok.Data;

@Data
public class BankAccount{
    private String banqueCode;
    private String counterNumber;
    private String accountNumber;
    private String ribKey;
    private String iban;
    private String bicSwift;
}
