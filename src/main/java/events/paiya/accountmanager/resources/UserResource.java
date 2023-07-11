package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.Organizer;
import events.paiya.accountmanager.enumerations.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResource {

    private String id;

    @NotEmpty(message = "Last name is mandatory")
    @Size(min = 2, max = 20, message = "Last name size must be between 2 and 20 caracters")
    private String lastname;

    @NotEmpty(message = "firstname is mandatory")
    @Size(min = 2, max = 30, message = "Last name size must be between 2 and 30 caracters")
    private String firstname;

    @Email(message = "Must be a valid email")
    private String email;

    @NotEmpty(message = "Gender is mandatory")
    private Gender gender;

    @NotEmpty(message = "Phone number is mandatory")
    private String phoneNumber;

    private Organizer organizer;

    private Address address;
}
