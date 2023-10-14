package events.paiya.accountmanager.domains;

import lombok.*;

import java.util.Objects;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMember {
    private String id;
    private String lastname;
    private String firstname;
    private String email;
    @Builder.Default
    private boolean isOrganizationOwner = false;
    @Builder.Default
    private boolean isAdmin = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationMember that = (OrganizationMember) o;
        return id.equals(that.id) && lastname.equals(that.lastname) && firstname.equals(that.firstname) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, email);
    }
}
