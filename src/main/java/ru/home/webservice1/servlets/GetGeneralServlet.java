package ru.home.webservice1.servlets;

import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class GetGeneralServlet extends HttpServlet {
    private StudentDaoJDBC userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new StudentDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> listStudent = userDAO.getAllStudent();
        req.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/user-list.jsp");
        dispatcher.forward(req, resp);
    }
}
