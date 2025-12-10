package org.pulsar.scoreboard.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pulsar.scoreboard.model.MatchScore;
import org.pulsar.scoreboard.service.MatchScoreService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;


@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private TemplateEngine templateEngine;
    private MatchScoreService matchScoreService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        templateEngine = (TemplateEngine) context.getAttribute("templateEngine");
        matchScoreService = (MatchScoreService) context.getAttribute("matchScoreService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matchId = request.getParameter("uuid");

        MatchScore matchScore = matchScoreService.get(matchId);
        Context context = configureContext(matchScore);

        templateEngine.process("match-score", context, response.getWriter());
    }

    private Context configureContext(MatchScore matchScore) {
        Context context = new Context();

        context.setVariable("match", matchScore.getMatch());
        context.setVariable("firstPlayer", matchScore.getFirstPlayerScore());
        context.setVariable("secondPlayer", matchScore.getSecondPlayerScore());

        return context;
    }
}
