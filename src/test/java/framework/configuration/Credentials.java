package framework.configuration;

import lombok.Data;

@Data
public class Credentials {
    private String barcode;
    private String pin;

    public String makeBaseForAuthHeader(String barcode, String pin) {
        this.barcode = barcode;
        this.pin = pin;
        return barcode + ":" + pin;
    }
}
