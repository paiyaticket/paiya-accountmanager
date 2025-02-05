package events.paiya.accountmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileMoneyAccount {
    private String countryPrefixNumber;
    private String phoneNumber;
    private String provider;
}
