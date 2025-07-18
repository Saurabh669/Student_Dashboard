package coms.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/updateStudent")
public class UpdateStudentServlet extends HttpServlet {

    private static final String JDBC_URL  = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Saurabh@123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int    rollNo      = Integer.parseInt(req.getParameter("rollNo"));
        String name        = req.getParameter("name");
        String branch      = req.getParameter("branch");
        String gender      = req.getParameter("gender");
        int    passingYear = Integer.parseInt(req.getParameter("passingYear"));
        String dob         = req.getParameter("dob");

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE students SET name=?, branch=?, gender=?, passing_year=?, dob=? WHERE roll_no=?")) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setString(1, name);
            ps.setString(2, branch);
            ps.setString(3, gender);
            ps.setInt   (4, passingYear);
            ps.setDate  (5, Date.valueOf(dob));
            ps.setInt   (6, rollNo);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        res.sendRedirect("getStudentDetails.html");
    }
}
