package ru.home.webservice1.servlets.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.dao.jdbc.CoordinatorDaoJDBC;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.utils.Dbutils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/api/updateCoordinator")
public class JSONUpdateCoordinatorServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CoordinatorDaoJDBC coordinatorDAO;
    /**
     * Для подключения к БД Testcontainers задаем логин, пароль БД контейнера, в случае запуска
     * локальной БД закомментировать все три поля
     */
    private final String namedb = "test";
    private final String passdb = "test";
    private Dbutils connectionProvider;

    @Override
    public void init() throws ServletException {
        /**
         * Для подключения к БД Testcontainers, чтобы подключаться к локальной БД,
         * надо убрать комментирование
         */
        // studentDAO = new StudentDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/student-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int id = Integer.parseInt(req.getParameter("id"));
        final String name = req.getParameter("name");
        Coordinator coordinator = new Coordinator(id, name);
        /**
         * Для подключения к БД Testcontainers получаем url БД контейнера, в случае подключения
         * к локальной БД все три строчки ниже закомментировать
         */
        final String URL = req.getParameter("url");
        connectionProvider = new Dbutils(URL, namedb, passdb);
        coordinatorDAO = new CoordinatorDaoJDBC(connectionProvider);
        coordinatorDAO.updateCoordinator(coordinator);
        System.out.println("Координатор обновлен");
    }
}