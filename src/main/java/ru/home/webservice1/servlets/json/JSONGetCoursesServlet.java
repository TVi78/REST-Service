package ru.home.webservice1.servlets.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.dao.jdbc.CourseDaoJDBC;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@WebServlet("/api/courses")
public class JSONGetCoursesServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CourseDaoJDBC courseDAO;
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
        //courseDAO = new CourseDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * Для подключения к БД Testcontainers получаем url БД контейнера, в случае подключения
         * к локальной БД все три строчки ниже закомментировать
         */
        final String URL = req.getParameter("url");
        connectionProvider = new Dbutils(URL, namedb, passdb);
        courseDAO = new CourseDaoJDBC(connectionProvider);
        List<Course> listCourse = courseDAO.getAllCourse();
        final String jsonStudent = objectMapper.writeValueAsString(listCourse);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(jsonStudent);
    }
}
