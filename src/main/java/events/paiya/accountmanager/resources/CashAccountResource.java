package events.paiya.accountmanager.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import events.paiya.accountmanager.enumerations.FinancialAccountType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        use = JsonTypeInfo.Id.NAME,
        property = "financialAccountType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value= BankAccountResource.class, name="BANK_ACCOUNT"),
        @JsonSubTypes.Type(value= CardAccountResource.class, name="CARD"),
        @JsonSubTypes.Type(value= MobileMoneyAccountResource.class, name="MOBILE_MONEY"),
        @JsonSubTypes.Type(value= DigitalWalletAccountResource.class, name="DIGITAL_WALLET")
})
@SuperBuilder
public abstract class CashAccountResource extends BaseResource{
    private String id;
    private FinancialAccountType financialAccountType;
    private Boolean isDefault;
    private String owner;

    public CashAccountResource() {
    }
}
