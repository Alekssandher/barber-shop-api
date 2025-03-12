package alekssandher.barber_shop_api.exceptions;

public class Exceptions {

    public static class BadRequestException extends Exception {
        public BadRequestException(String message) {
            super(message);
        }

        public BadRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ConflictException extends Exception {
        public ConflictException(String message) {
            super(message);
        }

        public ConflictException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ForbiddenException extends Exception {
        public ForbiddenException(String message) {
            super(message);
        }

        public ForbiddenException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    public static class NotFoundException extends Exception {
        public NotFoundException(String message) {
            super(message);
        }

        public NotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InternalErrorException extends Exception {
        public InternalErrorException(String message) {
            super(message);
        }

        public InternalErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ContentTooLargeException extends Exception {
        public ContentTooLargeException(String message) {
            super(message);
        }

        public ContentTooLargeException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    public static class TooManyRequestsException extends Exception {
        public TooManyRequestsException(String message) {
            super(message);
        }

        public TooManyRequestsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
