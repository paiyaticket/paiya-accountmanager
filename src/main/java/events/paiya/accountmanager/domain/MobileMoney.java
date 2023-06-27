package events.paiya.accountmanager.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MobileMoney extends FinancialAccount{
    private String countryPrefixNumber;
    private String phoneNumber;
    private String provider;
}
