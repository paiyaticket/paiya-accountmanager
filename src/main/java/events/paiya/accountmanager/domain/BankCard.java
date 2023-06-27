package events.paiya.accountmanager.domain;

import events.paiya.accountmanager.enumeration.BankCardType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BankCard extends FinancialAccount {
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;
    private BankCardType bankCardType;
    private String provider;
}
