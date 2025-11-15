// util/VnPayUtil.java
package util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class VnPayUtil {
    
    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Tạo chuỗi query & chuỗi dữ liệu để ký theo rule: sort theo tên tham số tăng dần */
    public static Map<String, String> buildSignedQuery(Map<String, String> fields, String hashSecret) {
        // sort by key
        List<String> keys = new ArrayList<>(fields.keySet());
        Collections.sort(keys);
        StringBuilder query = new StringBuilder();
        StringBuilder data = new StringBuilder();
        for (String k : keys) {
            String v = fields.get(k);
            if (v == null || v.isEmpty()) continue;
            // URL-encode key & value theo UTF-8
            query.append(URLEncoder.encode(k, StandardCharsets.UTF_8)).append("=")
                 .append(URLEncoder.encode(v, StandardCharsets.UTF_8)).append("&");
            data.append(k).append("=").append(v).append("&");
        }
        
        if (query.length() > 0) query.setLength(query.length() - 1);
        if (data.length() > 0) data.setLength(data.length() - 1);
        String secureHash = hmacSHA512(hashSecret, data.toString());
        Map<String, String> out = new HashMap<>();
        out.put("query", query.toString());
        out.put("signData", data.toString());
        out.put("secureHash", secureHash);
        return out;
    }
}
