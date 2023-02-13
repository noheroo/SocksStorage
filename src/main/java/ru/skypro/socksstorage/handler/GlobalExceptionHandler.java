package ru.skypro.socksstorage.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.socksstorage.exception.NeedDeleteMoreSocksThanExistException;
import ru.skypro.socksstorage.exception.OperationIsBlankException;
import ru.skypro.socksstorage.exception.SocksNotFoundException;
import ru.skypro.socksstorage.exception.WrongOperationException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NeedDeleteMoreSocksThanExistException.class)
    public ResponseEntity<String> needDeleteMoreSocksThanExistExceptionHandler(NeedDeleteMoreSocksThanExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Need to delete more socks than exist");
    }

    @ExceptionHandler(SocksNotFoundException.class)
    public ResponseEntity<String> socksNotFoundExceptionHandler(SocksNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Socks not found");
    }
    @ExceptionHandler(WrongOperationException.class)
    public ResponseEntity<String> wrongOperationExceptionHandler(WrongOperationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Sent request with wrong operation");
    }
    @ExceptionHandler(OperationIsBlankException.class)
    public ResponseEntity<String> operationIsBlankExceptionHandler(OperationIsBlankException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Sent request with blank operation");
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        List<ConstraintViolation<?>> errorList = e.getConstraintViolations().stream().toList();
        for (ConstraintViolation<?> constraintViolation : errorList) {
            errors.add("input value: " + StringUtils.substringAfter(constraintViolation.getPropertyPath().toString(), ".") + "="
                    + constraintViolation.getInvalidValue().toString() + ", error: " + constraintViolation.getMessageTemplate());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors.toString());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < e.getBindingResult().getErrorCount(); i++) {
           errors.add("input value: "+ e.getFieldErrors().get(i).getField() +"="
                   + e.getFieldErrors().get(i).getRejectedValue() +", error: "+ e.getFieldErrors().get(i).getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors.toString());
    }

}
