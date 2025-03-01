package events.paiya.accountmanager.enumerations;

import lombok.Getter;

@Getter
public enum DigitalWalletProvider {
    PAYPAL("Paypal"), 
    OTHER("other");

    private final String label;

    DigitalWalletProvider(String label) {
        this.label = label;
    }
}
