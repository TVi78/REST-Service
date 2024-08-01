package ru.home.webservice1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonStudentServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private StudentDaoJDBC studentDaoJDBC;

    @Override
    public void init() throws ServletException {
        final Object studentDaoJDBC = getServletContext().getAttribute("studentDaoJDBC");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
