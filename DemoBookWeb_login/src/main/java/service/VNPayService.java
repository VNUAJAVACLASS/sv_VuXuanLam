package service;

import config.VNPayConfig;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VNPayService {

    /**
     * HMAC-SHA512 hash function
     */
    private static String hmacSHA512(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    // ==================================================================
    // 1. CREATE PAYMENT URL
    // ==================================================================
    public String createPaymentUrl(long orderId, long amount, String orderInfo, String ipAddress) throws Exception {
        String vnp_TxnRef = orderId + "_" + System.currentTimeMillis();
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Expire in 15 minutes
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());

        // Use TreeMap to auto-sort keys (VNPay requirement)
        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.VNP_VERSION);
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", VNPayConfig.VNP_ORDER_TYPE);
        vnp_Params.put("vnp_Locale", VNPayConfig.VNP_LOCALE);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddress);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            String value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                String encoded = URLEncoder.encode(value, StandardCharsets.US_ASCII);
                if (!first) {
                    hashData.append("&");
                    query.append("&");
                }
                hashData.append(entry.getKey()).append("=").append(encoded);
                query.append(entry.getKey()).append("=").append(encoded);
                first = false;
            }
        }

        String secureHash = hmacSHA512(hashData.toString(), VNPayConfig.vnp_HashSecret);
        query.append("&vnp_SecureHash=").append(secureHash);

        return VNPayConfig.vnp_PayUrl + "?" + query.toString();
    }

    // ==================================================================
    // 2. EXTRACT PARAMETERS FROM REQUEST
    // ==================================================================
    public Map<String, String> extractParameters(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            if (value != null && !value.trim().isEmpty()) {
                params.put(name, value.trim());
            }
        }
        return params;
    }

    // ==================================================================
    // 3. VALIDATE CHECKSUM (vnp_SecureHash)
    // ==================================================================
    public boolean validateChecksum(Map<String, String> params) throws Exception {
        if (!params.containsKey("vnp_SecureHash")) return false;

        String receivedHash = params.remove("vnp_SecureHash");
        Map<String, String> sorted = new TreeMap<>(params);

        StringBuilder data = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> e : sorted.entrySet()) {
            String v = e.getValue();
            if (v != null && !v.isEmpty()) {
                if (!first) data.append("&");
                data.append(e.getKey()).append("=")
                    .append(URLEncoder.encode(v, StandardCharsets.US_ASCII));
                first = false;
            }
        }

        String calculated = hmacSHA512(data.toString(), VNPayConfig.vnp_HashSecret);
        return calculated.equalsIgnoreCase(receivedHash);
    }

    // ==================================================================
    // 4. CHECK TRANSACTION STATUS
    // ==================================================================
    public boolean isPaymentSuccess(Map<String, String> params) {
        return "00".equals(params.get("vnp_ResponseCode"));
    }

    public String getResponseMessage(Map<String, String> params) {
        String code = params.get("vnp_ResponseCode");
        if (code == null) return "Không có mã phản hồi";

        return switch (code) {
            case "00" -> "Giao dịch thành công";
            case "01" -> "Giao dịch đã tồn tại";
            case "02" -> "Merchant không hợp lệ";
            case "04" -> "Khởi tạo GD không thành công";
            case "07" -> "Giao dịch bị nghi ngờ gian lận";
            case "09" -> "Giao dịch không thành công do tài khoản bị khóa";
            case "10" -> "Sai OTP";
            case "11" -> "Hết hạn giao dịch";
            case "24" -> "Người dùng hủy giao dịch";
            default -> "Lỗi không xác định: " + code;
        };
    }

    // ==================================================================
    // 5. GET CLIENT IP (for vnp_IpAddr)
    // ==================================================================
    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null ? ip.split(",")[0].trim() : "127.0.0.1";
    }
}