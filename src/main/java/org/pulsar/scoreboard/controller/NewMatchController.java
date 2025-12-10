package org.pulsar.scoreboard.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pulsar.scoreboard.dto.MatchCreateRequest;
import org.pulsar.scoreboard.service.MatchScoreService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.UUID;


@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {

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
        templateEngine.process("new-match", new Context(), response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MatchCreateRequest createRequest = buildCreateRequest(request);

        UUID matchId = matchScoreService.create(createRequest);
        response.sendRedirect("/match-score?uuid=%s".formatted(matchId));
    }

    private MatchCreateRequest buildCreateRequest(HttpServletRequest request) {
        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        return MatchCreateRequest.builder()
                .firstPlayerName(firstPlayerName)
                .secondPlayerName(secondPlayerName)
                .build();
    }
}
