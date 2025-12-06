package org.pulsar.scoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
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
        ITemplateResolver resolver = createTemplateResolver(context);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        return templateEngine;
    }

    private ITemplateResolver createTemplateResolver(ServletContext context) {
        JakartaServletWebApplication webApplication = JakartaServletWebApplication.buildApplication(context);
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(webApplication);

        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCacheable(true);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return resolver;
    }
}
