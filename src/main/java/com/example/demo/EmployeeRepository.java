package com.example.demo;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Rudoi
 * @version 2.0 11 May 2023
 */
public class EmployeeRepository {

    /*public static void main(String[] args) {
        getConnection();

        Employee employee = new Employee();

        employee.setName("oleg");
        employee.setEmail(" ");
        employee.setCountry(" ");
        save(employee);
    }*/

    private static Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/employee";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static PrintWriter out = new PrintWriter(System.out);

    private static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (connection != null) {
                out.println("Connected to the PostgreSQL server successfully.");
            } else {
                out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            out.println(e);
        }

        return connection;
    }

    public static int save(Employee employee) {
        String statement = "insert into users(name,email,country) values (?,?,?)";

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(out);
        }

        return 0;
    }

    public static int update(Employee employee) {
        String statement = "update users set name=?,email=?,country=? where id=?";

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setInt(4, employee.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(out);
        }

        return 0;
    }

    public static int delete(int id) {
        String statement = "delete from users where id=?";

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setInt(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(out);
        }

        return 0;
    }

    public static Employee getEmployeeById(int id) {
        Employee employee = null;
        String statement = "select * from users where id=?";

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = EmployeeRepository.getEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(out);
        }

        return employee;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> listEmployees = new ArrayList<>();
        String statement = "select * from users limit 50";

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee employee = EmployeeRepository.getEmployee(rs);

                listEmployees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace(out);
        }

        for (Employee employee : listEmployees) {
            out.print(employee);
        }

        return listEmployees;
    }

    private static Employee getEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();

        employee.setId(rs.getInt(1));
        employee.setName(rs.getString(2));
        employee.setEmail(rs.getString(3));
        employee.setCountry(rs.getString(4));

        return employee;
    }
}
