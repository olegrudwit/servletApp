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
@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        Employee employee = getEmployee(request);

        int status = EmployeeRepository.save(employee);

        String answer = status > 0
                ? "Record saved successfully!"
                : "Sorry! unable to save record";
        out.println(answer);
        out.close();
    }

    private static Employee getEmployee(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        Employee employee = new Employee();

        employee.setName(name);
        employee.setEmail(email);
        employee.setCountry(country);
        return employee;
    }
}
