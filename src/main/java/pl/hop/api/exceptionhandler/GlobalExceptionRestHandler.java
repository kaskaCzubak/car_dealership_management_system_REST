package pl.hop.api.exceptionhandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.hop.domain.exception.NotFoundException;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionRestHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
            ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
            DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            NotFoundException.class, HttpStatus.NOT_FOUND
    );
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception exception,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request
    ) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}", errorId, statusCode, exception);
        return super.handleExceptionInternal(exception, ExceptionMessage.of(errorId), headers, statusCode, request);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception exception) {

        return doHandle(exception, getHttpStatusFromException(exception.getClass()));
    }

    private HttpStatus getHttpStatusFromException(final Class<?> exception) {
        return EXCEPTION_STATUS.getOrDefault(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> doHandle(final Exception exception, final HttpStatus httpStatus){
       final String errorId = UUID.randomUUID().toString();
       log.error("Exception: ID={}, HttpStatus={}", errorId, httpStatus, exception);

       return ResponseEntity
               .status(httpStatus)
               .contentType(MediaType.APPLICATION_JSON)
               .body(ExceptionMessage.of(errorId));
   }

}
