package coms.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

    private static final String JDBC_URL  = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Saurabh@123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int rollNo = Integer.parseInt(req.getParameter("rollNo"));

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE roll_no = ?")) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, rollNo);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        /* tell JS something simple or just redirect */
        res.getWriter().print("Deleted!");
    }
}
