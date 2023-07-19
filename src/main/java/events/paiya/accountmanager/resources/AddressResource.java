package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressResource {
    private Country country;
    private String city;
    private String state;
    private String postal;
    private String addressDetail;
}
