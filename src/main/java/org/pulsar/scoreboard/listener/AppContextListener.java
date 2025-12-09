package org.pulsar.scoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.nio.charset.StandardCharsets;


@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        TemplateEngine templateEngine = createTemplateEngine(context);
        context.setAttribute("templateEngine", templateEngine);
    }

    private TemplateEngine createTemplateEngine(ServletContext context) {
        TemplateEngine templateEngine = new TemplateEngine();
        ITemplateResolver templateResolver = createTemplateResolver(context);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private ITemplateResolver createTemplateResolver(ServletContext context) {
        JakartaServletWebApplication webApplication = JakartaServletWebApplication.buildApplication(context);
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(webApplication);

        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);

        return templateResolver;
    }
}
