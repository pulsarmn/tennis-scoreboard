package org.pulsar.scoreboard.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebFilter("/*")
public class NotFoundFilter implements Filter {

    private TemplateEngine templateEngine;

    private static final String NOT_FOUND_TEMPLATE = "404";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        templateEngine = (TemplateEngine) context.getAttribute("templateEngine");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(httpResponse) {

            @Override
            public void sendError(int status, String msg) throws IOException {
                if (status == SC_NOT_FOUND) {
                    renderNotFound((HttpServletResponse) getResponse());
                } else {
                    super.sendError(status, msg);
                }
            }

            @Override
            public void sendError(int status) throws IOException {
                if (status == SC_NOT_FOUND) {
                    renderNotFound((HttpServletResponse) getResponse());
                } else {
                    super.sendError(status);
                }
            }
        };

        chain.doFilter(request, wrapper);
    }

    private void renderNotFound(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        templateEngine.process(NOT_FOUND_TEMPLATE, new Context(), response.getWriter());
    }
}
