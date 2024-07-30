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

@WebServlet("/newStudent")
public class AddStudentServlet extends HttpServlet {
    private StudentDaoJDBC studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/student-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String name = req.getParameter("name");
        final int coordinator_id = Integer.parseInt(req.getParameter("coord_id"));

        Student student = new Student(name, coordinator_id);
        studentDAO.addStudent(student);
        System.out.println("Пользователь добавлен");
        resp.sendRedirect("/");
    }
}
