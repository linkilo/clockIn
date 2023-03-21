package cn.nineSeven.handler;

import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.entity.Result;
import cn.nineSeven.handler.exception.SystemException;
import cn.nineSeven.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e){
        log.error("出现了异常！ {}",e);
        return Result.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){

        log.error("{}",e);
        Result result = null;
        if(e instanceof BadCredentialsException){
            result = Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else if(e instanceof AccessDeniedException){
            result = Result.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        } else{
            result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return result;
    }

}
