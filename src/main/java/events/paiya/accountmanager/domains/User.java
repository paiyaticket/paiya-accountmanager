package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter @Setter
public class User {
    @Id
    private String id;
    @NotEmpty(message = "Last name is mandatory")
    @Size(min = 2, max = 20, message = "Last name size must be between 2 and 20 caracters")
    private String lastname;

    @NotEmpty(message = "firstname is mandatory")
    @Size(min = 2, max = 30, message = "Last name size must be between 2 and 30 caracters")
    private String firstname;

    @Email(message = "Must be a valid email")
    private String email;

    private Gender gender;

    @NotEmpty(message = "Phone number is mandatory")
    private String phoneNumber;
    private Organizer organizer;
    private Address address;
    private List<FinancialOperationAccount> financialOperationAccounts;
    private boolean isEnabled;

    public void enableUser(){
        this.isEnabled = true;
    }

    public void disableUser(){
        this.isEnabled = false;
    }

    @PersistenceCreator
    public User(String id, String lastname, String firstname, String email, Gender gender, String phoneNumber, Organizer organizer, Address address) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.organizer = organizer;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", organizer=" + organizer +
                ", adress=" + address +
                '}';
    }

}
