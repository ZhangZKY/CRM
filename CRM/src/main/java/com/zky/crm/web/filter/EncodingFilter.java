package com.zky.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");

        servletResponse.setContentType("test/html;charset=utf-8");

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
