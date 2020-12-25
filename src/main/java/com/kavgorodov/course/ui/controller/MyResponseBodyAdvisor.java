package com.kavgorodov.course.ui.controller;

import com.kavgorodov.course.security.SecurityConstants;
import com.kavgorodov.course.ui.model.response.UserRest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vladimir Vishnevskiy
 */
@ControllerAdvice
public class MyResponseBodyAdvisor implements ResponseBodyAdvice<UserRest> {

    //HttpServletRequest req;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType().equals(UserRest.class);
    }

    @Override
    public UserRest beforeBodyWrite( UserRest body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String fields = ((ServletServerHttpRequest) request).getServletRequest().getParameter("fields");
    //    String header = req.getHeader("fields");
        // body.set...(null) for each field not in fields

        return body;
    }

//    private String getFields() {
//        return req.getHeader("fields");
//    }

}