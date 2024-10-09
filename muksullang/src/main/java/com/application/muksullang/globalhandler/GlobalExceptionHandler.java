package com.application.muksullang.globalhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(MissingServletRequestParameterException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", "잘못된 요청입니다. 필수 매개변수가 누락되었습니다.");
        return "error/400";  // 400 오류 페이지로 이동
    }

    // 404 Not Found 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", "요청하신 페이지를 찾을 수 없습니다.");
        return "error/404";  // 404 오류 페이지로 이동
    }

    // 500 Internal Server Error - 커스텀 예외 처리
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerErrorException(ServerErrorException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", e.getMessage());  // 예외 메시지를 사용자에게 보여줌
        return "error/500";  // 500 오류 페이지로 이동
    }

    // 500 Internal Server Error - 일반적인 NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNullPointerException(NullPointerException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        return "error/500";  // 500 오류 페이지로 이동
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", "서버에서 오류가 발생했습니다.");
        return "error/500";
    }
}
