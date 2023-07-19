package events.paiya.accountmanager.domains;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private Country country;
    private String city;
    private String state;
    private String postal;
    private String addressDetail;
}