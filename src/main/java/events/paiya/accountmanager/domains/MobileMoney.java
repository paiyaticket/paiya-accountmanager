package events.paiya.accountmanager.domains;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MobileMoney extends FinancialOperationAccount {
    private String countryPrefixNumber;
    private String phoneNumber;
    private String provider;
}
