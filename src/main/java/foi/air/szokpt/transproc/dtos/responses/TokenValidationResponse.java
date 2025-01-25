package foi.air.szokpt.transproc.dtos.responses;

import java.util.List;

public class TokenValidationResponse {
    private boolean success;
    private String message;
    private List<Role> data;

    public TokenValidationResponse(boolean success, String message, List<Role> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Role> getData() {
        return data;
    }

    public void setData(List<Role> data) {
        this.data = data;
    }

    public String getRole() {
        if (data != null && !data.isEmpty()) {
            return data.getFirst().getRole();
        }
        return null;
    }

    public static class Role {
        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}

