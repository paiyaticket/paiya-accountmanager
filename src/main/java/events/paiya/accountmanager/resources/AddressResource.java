package events.paiya.accountmanager.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import events.paiya.accountmanager.domains.Country;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResource{
    private Country country;
    private String city;
    private String state;
    private String postal;
    private String addressDetail;
}
