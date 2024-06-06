package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.enumerations.Gender;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResource{

    private String id;
    private String lastname;
    private String firstname;
    @Email(message = "Must be a valid email")
    private String email;
    private Gender gender;
    private String phoneNumber;

    @Builder.Default
    private boolean active = true;

    private AddressResource address;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;

}
