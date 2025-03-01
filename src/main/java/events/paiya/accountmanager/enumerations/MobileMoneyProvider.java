package events.paiya.accountmanager.enumerations;

import lombok.Getter;


@Getter
public enum MobileMoneyProvider {
    ORANGE_MONEY_CI("Orange Côte d'Ivoire"),
    MTN_MONEY_CI("MTN Côte d'Ivoire"),
    MOOV_MONEY_CI("MOOV Côte d'Ivoire"),
    WAVE_CI("Wave Côte d'Ivoire");

    private final String label;

    MobileMoneyProvider(String label) {
        this.label = label;
    }
}
