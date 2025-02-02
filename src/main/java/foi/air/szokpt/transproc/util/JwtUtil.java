package foi.air.szokpt.transproc.util;

import org.json.JSONObject;

import java.util.Base64;

public class JwtUtil {
    private static String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    public static String getUsername(String authorizationHeader) {
        String token = extractToken(authorizationHeader);

        try {
            String[] parts = token.split("\\.");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token format.");
            }
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject payloadJson = new JSONObject(payload);

            return payloadJson.optString("sub");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
