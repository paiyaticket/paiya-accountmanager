package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.CardProvider;
import lombok.Data;

import java.util.Date;

@Data
public class CreditCardAccount {
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;
    private CardProvider provider;
}
