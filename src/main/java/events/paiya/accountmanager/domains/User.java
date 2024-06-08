package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.Gender;
import lombok.*;
import lombok.Builder.Default;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    private String id;
    private String lastname;
    private String firstname;
    private String displayname;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    @Indexed(unique = true)
    private String phoneNumber;
    private Address address;
    private List<FinancialAccount> financialAccounts;
    @Builder.Default
    private boolean active = true;

    // Auditing
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;

    public void enableUser(){
        this.active = true;
    }

    public void disableUser(){
        this.active = false;
    }

}
