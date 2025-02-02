package foi.air.szokpt.transproc.util;

import foi.air.szokpt.transproc.dtos.responses.ApiResponse;

import java.util.List;

public class ApiResponseUtil {
    public static <T> ApiResponse<T> successWithData(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> successWithData(String message, List<T> data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message);
    }
}
