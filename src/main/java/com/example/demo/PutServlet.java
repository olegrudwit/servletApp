package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Oleg Rudoi
 * @version 2.0 11 May 2023
 */
@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Employee employee = getEmployee(request);

        int status = EmployeeRepository.update(employee);
        String answer = status > 0
                ? "Record saved successfully!"
                : "Sorry! unable to save record";
        out.println(answer);
        out.close();
    }

    private static Employee getEmployee(HttpServletRequest request) {
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Employee employee = new Employee();

        employee.setId(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setCountry(request.getParameter("country"));

        return employee;
    }
}
