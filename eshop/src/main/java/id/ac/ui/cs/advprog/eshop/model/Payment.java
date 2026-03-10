package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    @Setter
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(method, paymentData);
    }

    private String determineStatus(String method, Map<String, String> data) {
        if ("VOUCHER_CODE".equals(method)) {
            return validateVoucher(data.get("voucherCode"))
                    ? "SUCCESS"
                    : "REJECTED";
        } else if ("BANK_TRANSFER".equals(method)) {
            return validateBankTransfer(data)
                    ? "SUCCESS"
                    : "REJECTED";
        }
        return "REJECTED";
    }

    private boolean validateVoucher(String code) {
        if (code == null || code.length() != 16) {
            return false;
        }
        if (!code.startsWith("ESHOP")) {
            return false;
        }
        long digitCount = code.chars().filter(Character::isDigit).count();
        return digitCount == 8;
    }

    private boolean validateBankTransfer(Map<String, String> data) {
        String bankName = data.get("bankName");
        String referenceCode = data.get("referenceCode");
        return bankName != null && !bankName.isEmpty()
                && referenceCode != null && !referenceCode.isEmpty();
    }

}