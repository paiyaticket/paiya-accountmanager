package events.paiya.accountmanager.domains.projections;

public record OrganizationMemberProjection(String id,
                                           String lastname,
                                           String firstname,
                                           String email) {
}
