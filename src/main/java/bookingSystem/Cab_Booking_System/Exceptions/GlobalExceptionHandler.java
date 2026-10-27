package bookingSystem.Cab_Booking_System.Exceptions;

import bookingSystem.Cab_Booking_System.Dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());
        logger.warn("Entity not found: {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String message = "";
        for(FieldError fieldError : fieldErrors) {
            message = message + fieldError.getField() + ": " + fieldError.getDefaultMessage();
        }
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    message,
                    request.getRequestURI());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(EmailAlreadyExistsException ex,HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(LicenseNumberAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleDuplicateLicense(LicenseNumberAlreadyExists ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(CabNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleCabNotAvailable(CabNotAvailableException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
