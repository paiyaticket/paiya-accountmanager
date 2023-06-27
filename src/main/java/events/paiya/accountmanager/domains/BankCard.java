package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.BankCardType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BankCard extends FinancialOperationAccount {
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;
    private BankCardType bankCardType;
    private String provider;
}
