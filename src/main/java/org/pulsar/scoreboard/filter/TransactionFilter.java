package org.pulsar.scoreboard.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;


@WebFilter("/*")
public class TransactionFilter implements Filter {

    private SessionFactory sessionFactory;
    private TemplateEngine templateEngine;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        sessionFactory = (SessionFactory) context.getAttribute("sessionFactory");
        templateEngine = (TemplateEngine) context.getAttribute("templateEngine");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.equals("/") || requestURI.equals("/home") || (requestURI.equals("/new-match") && ((HttpServletRequest) request).getMethod().equals("GET")) || requestURI.startsWith("/static")) {
            chain.doFilter(request, response);
        } else {
            executeInTransaction(request, response, chain);
        }
    }

    private void executeInTransaction(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        Transaction transaction = null;
        try(Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            chain.doFilter(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            templateEngine.process("500", new Context(), response.getWriter());
        }
    }
}
