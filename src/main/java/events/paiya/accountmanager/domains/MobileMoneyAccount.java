package events.paiya.accountmanager.domains;

import lombok.Data;

@Data
public class MobileMoneyAccount {
    private String countryPrefixNumber;
    private String phoneNumber;
    private String provider;
}
