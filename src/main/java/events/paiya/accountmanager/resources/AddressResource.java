package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.Country;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@SuperBuilder
public class AddressResource extends BaseResource{
    private Country country;
    private String city;
    private String state;
    private String postal;
    private String addressDetail;
}
