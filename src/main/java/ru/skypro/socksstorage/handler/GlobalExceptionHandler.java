package ru.skypro.socksstorage.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.socksstorage.exception.NeedDeleteMoreSocksThanExistException;
import ru.skypro.socksstorage.exception.SocksNotFoundException;
import ru.skypro.socksstorage.exception.WrongOperationException;

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
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

}
