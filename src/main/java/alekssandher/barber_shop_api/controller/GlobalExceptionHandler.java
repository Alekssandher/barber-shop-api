package alekssandher.barber_shop_api.controller;

import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
import jakarta.validation.ConstraintViolationException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request)
    {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                .collect(Collectors.toList());

        ErrorDetails error = new BadRequest(request, "Erro de Validação", String.join("; ", errors));
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request)
    {
        List<String> errors = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                .collect(Collectors.toList());

        ErrorDetails error = new BadRequest(request, "Constraint Violation", String.join("; ", errors));
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex, HttpServletRequest request)
    {
        ErrorDetails error = new InternalErrorCustom(request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) 
    {
        String message = String.format("O parâmetro '%s' precisa ser do tipo %s.", ex.getName(), ex.getRequiredType().getSimpleName());
        ErrorDetails error = new BadRequest(request, "Bad Request", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorDetails> handleDateTimeException(DateTimeException ex, HttpServletRequest request) {
        ErrorDetails error = new BadRequest(request, "Bad Request", "Ano ou mês inválido.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request)
    {
        ErrorDetails error = new BadRequest(request, "Bad Request", "Um valor requerido está faltando do path.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
