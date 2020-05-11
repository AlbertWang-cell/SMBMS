package com.smbms.controller;

import com.smbms.Exception.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public String handleException(Model model,Exception e){
        System.out.println("---------------"+e.getMessage());
        model.addAttribute("msg","编译异常--"+e.getMessage());
        return "error";
    }
    @ExceptionHandler(UserException.class)
    public String handleUserException(Model model,Exception e){
        System.out.println("-----------------"+e.getMessage());
        model.addAttribute("msg","用户异常"+e.getMessage());
        return "error";
    }
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Model model,Exception e){
        System.out.println("--------------------"+e.getMessage());
        model.addAttribute("msg","运行时异常"+e.getMessage());
        return "error";
    }
}
