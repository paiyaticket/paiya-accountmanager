package events.paiya.accountmanager.domains;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@SuperBuilder
public abstract class FinancialAccount {
    @Id
    private String id;
    @Builder.Default
    private Boolean isDefault = false;
    private String ownerId;

    public FinancialAccount() {
    }
}
