package org.pulsar.scoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pulsar.scoreboard.model.Match;
import org.pulsar.scoreboard.model.Player;
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

        SessionFactory sessionFactory = createSessionFactory();
        context.setAttribute("sessionFactory", sessionFactory);
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

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(Match.class);
        configuration.configure("hibernate.cfg.xml");

        return configuration.buildSessionFactory();
    }
}
