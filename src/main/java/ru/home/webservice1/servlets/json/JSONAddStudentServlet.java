package ru.home.webservice1.servlets.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/api/addStudent")
public class JSONAddStudentServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private StudentDaoJDBC studentDAO;
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
        //studentDAO = new StudentDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/student-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  //      req.setCharacterEncoding("UTF-8");
        final String name = req.getParameter("name");
        final int coordinator_id = Integer.parseInt(req.getParameter("coord_id"));
        Student student = new Student(name, coordinator_id);
        /**
         * Для подключения к БД Testcontainers получаем url БД контейнера, в случае подключения
         * к локальной БД все три строчки ниже закомментировать
         */
        final String URL = req.getParameter("url");
        connectionProvider = new Dbutils(URL, namedb, passdb);
        studentDAO = new StudentDaoJDBC(connectionProvider);

        studentDAO.addStudent(student);
        System.out.println("Студент добавлен");
    }
}
