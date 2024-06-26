package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.OrganizationMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberBundleResource {
    private String eventOrganizerId;
    private List<OrganizationMember> memberList;
    private List<String> memberEmailList;
}
