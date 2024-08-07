package events.paiya.accountmanager.domains;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import events.paiya.accountmanager.enumerations.FinancialAccountType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class CashAccount{
    @Id
    private String id;
    @NotNull
    private FinancialAccountType financialAccountType;
    @Builder.Default
    private Boolean isDefault = false;
    private String owner;

    // audit
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
}
