package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.Country;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressResource extends BaseResource{
    private Country country;
    private String city;
    private String state;
    private String postal;
    private String addressDetail;
}
