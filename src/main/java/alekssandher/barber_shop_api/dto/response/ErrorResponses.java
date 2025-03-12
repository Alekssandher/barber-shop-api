package alekssandher.barber_shop_api.dto.response;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorResponses {
    public static class InternalErrorCustom extends ErrorDetails {
        public InternalErrorCustom(HttpServletRequest request) {
            super(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                "Something went wrong at our side",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.500",
                request.getRequestURI()
            );
        }
    }
    
    public static class BadRequest extends ErrorDetails {
        public BadRequest(HttpServletRequest request, String title, String detail) {
            super(
                HttpStatus.BAD_REQUEST.value(),
                title != null ? title : "Bad Request",
                detail != null ? detail : "Request Malformed",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.400",
                request.getRequestURI()
            );
        }
    }
    
    public static class Forbidden extends ErrorDetails {
        public Forbidden(HttpServletRequest request, String detail) {
            super(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                detail != null ? detail : "Operation Not Authorized",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.403",
                request.getRequestURI()
            );
        }
    }

    public static class NotFound extends ErrorDetails {
        public NotFound(HttpServletRequest request, String title, String detail) {
            super(
                HttpStatus.NOT_FOUND.value(),
                title != null ? title : "Not Found",
                detail != null ? detail : "We Couldn't Find Your Request.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.404",
                request.getRequestURI()
            );
        }
    }
    
    public class ContentTooLarge extends ErrorDetails {
        public ContentTooLarge(HttpServletRequest request, String title, String detail) {
            super(
                HttpStatus.URI_TOO_LONG.value(),
                title != null ? title : "Request Too Long",
                detail != null ? detail : "Too Long Requisition.",
                "https://datatracker.ietf.org/doc/html/rfc9110#status.414",
                request.getRequestURI()
            );
        }
    }
    
    public class TooManyRequests extends ErrorDetails {
        public TooManyRequests(HttpServletRequest request, String title, String detail) {
            super(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                title != null ? title : "Too Many Requests",
                detail != null ? detail : "You are under cooldown or rate limit, try again later.",
                "https://datatracker.ietf.org/doc/html/rfc6585#section-4",
                request.getRequestURI()
            );
        }
    }
    
}   