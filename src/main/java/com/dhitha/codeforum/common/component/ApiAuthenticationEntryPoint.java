package com.dhitha.codeforum.common.component;

import com.dhitha.codeforum.common.model.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//    ApiError error =
//        new ApiError(
//            HttpStatus.UNAUTHORIZED, "", new ArrayList<>(Arrays.asList(e.getLocalizedMessage())));
//    httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(error));
  }
}