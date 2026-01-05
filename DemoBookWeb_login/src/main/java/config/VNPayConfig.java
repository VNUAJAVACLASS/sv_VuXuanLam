package config;

public class VNPayConfig {
    public static final String vnp_TmnCode = "LBMEROG7";
    public static final String vnp_HashSecret = "8ODEGEIEU7HZC9QSCZN1XKNZUFNU1QB5";
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ReturnUrl = "http://localhost:8080/DemoBookWeb_login/payment-return";

    // Recommended constants
    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_ORDER_TYPE = "other";
    public static final String VNP_LOCALE = "vn";
}