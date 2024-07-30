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

@WebServlet("/editStudent")
public class UpdateStudentServlet extends HttpServlet {
    private StudentDaoJDBC studentDAO;
    private int id;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDaoJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        id = Integer.parseInt(req.getParameter("id"));
        Student existingUser = studentDAO.getByIdStudent(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/student-form.jsp");
        req.setAttribute("student", existingUser);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String name = req.getParameter("name");
        final int coordinator_id = Integer.parseInt(req.getParameter("coord_id"));

        Student student = new Student(id, name, coordinator_id);
        studentDAO.updateStudent(student);
        System.out.println("Пользователь обновлен");
        resp.sendRedirect("/");
    }
}
