package alekssandher.barber_shop_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import alekssandher.barber_shop_api.dto.response.ErrorDetails;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.BadRequest;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.Forbidden;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.barber_shop_api.dto.response.ErrorResponses.NotFound;
import alekssandher.barber_shop_api.exceptions.Exceptions.BadRequestException;
import alekssandher.barber_shop_api.exceptions.Exceptions.ForbiddenException;
import alekssandher.barber_shop_api.exceptions.Exceptions.InternalErrorException;
import alekssandher.barber_shop_api.exceptions.Exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(NotFoundException ex, HttpServletRequest request)
    {
        ErrorDetails error = new NotFound(request, "Not Found", ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(BadRequestException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request, "Bad Request", ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(InternalErrorException ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(ForbiddenException ex, HttpServletRequest request)
    {
        ErrorDetails error = new Forbidden(request, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
