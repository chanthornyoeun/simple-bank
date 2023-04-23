package com.example.bank.exceptions;

import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { SQLException.class})
    public ResponseEntity<ResponseDTO> handleSQLException(SQLException ex) {
        String errorMessage = "An error occurred while processing your request: " + ex.getMessage();
        ResponseDTO response = ResponseUtil.object(null, false, errorMessage);
        LOGGER.error(errorMessage);
        ex.printStackTrace();
        return new ResponseEntity<ResponseDTO>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<ResponseDTO> handleNotFound(NoSuchElementException ex) {
        String errorMessage = "An error occurred while processing your request: " + ex.getMessage();
        ResponseDTO response = ResponseUtil.object(null, false, errorMessage);
        LOGGER.error(errorMessage);
        ex.printStackTrace();
        return new ResponseEntity<ResponseDTO>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(Exception ex) {
        String errorMessage = "An error occurred while processing your request: " + ex.getMessage();
        ResponseDTO response = ResponseUtil.object(null, false, errorMessage);
        LOGGER.error(errorMessage);
        ex.printStackTrace();
        return new ResponseEntity<ResponseDTO>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
