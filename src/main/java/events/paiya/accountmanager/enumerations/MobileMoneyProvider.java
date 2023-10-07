package events.paiya.accountmanager.enumerations;

import lombok.Getter;


@Getter
public enum MobileMoneyProvider {
    MTN_CI("MTN Côte d'Ivoire"),
    ORANGE_CI("Orange Côte d'Ivoire"),
    FLOOZ_CI("Flooz Côte d'Ivoire"),
    WAVE_CI("Wave Côte d'Ivoire");

    private final String label;

    MobileMoneyProvider(String label) {
        this.label = label;
    }
}
