package alekssandher.barber_shop_api.dto.response;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class ApiResponseDto {
    public static class OkResponse<T> extends ApiResponse<T> {

        public OkResponse(HttpServletRequest request, String title, String detail, T data) {
            super(
                HttpStatus.OK.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-200-ok",
                title != null ? title : "Request Successful",
                detail != null ? detail : "Request fetched successfully.",
                request.getRequestURI(),
                data 
            );
        }
    }

    public static class NoContentResponse extends ApiResponse<Void> {

        public NoContentResponse(HttpServletRequest request) {
            super(
                HttpStatus.NO_CONTENT.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-204-no-content",
                "No Content",
                "The request was successful, but there is no content to return.",
                request.getRequestURI(),
                null
            );
        }
    }

    public static class CreatedResponse extends ApiResponse<Void> {

        public CreatedResponse(HttpServletRequest request) {
            super(
                HttpStatus.CREATED.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-201-created",
                "Created",
                "The request was successful, and the resource was created.",
                request.getRequestURI(),
                null
            );
        }
    }

    public static class DeleteResponse extends ApiResponse<Void> {
        
        public DeleteResponse(HttpServletRequest request) {
            super(
                HttpStatus.NO_CONTENT.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-204-no-content",
                "Deleted",
                "The request was successful, and the resource was deleted.",
                request.getRequestURI(),
                null
            );
        }
    }
    
    public static class GetResponse<T> extends ApiResponse<T> {

        public GetResponse(T data, HttpServletRequest request) {
            super(
                HttpStatus.OK.value(),
                "https://datatracker.ietf.org/doc/html/rfc9110#name-200-ok",
                "Request Successful",
                "Request data retrieved successfully.",
                request.getRequestURI(),
                data
            );
        }
    }
}
