package coms.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet("/getStudents")
public class GetStudentDetailsServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Saurabh@123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String query = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>Roll No</th><th>Name</th><th>Branch</th><th>Gender</th><th>Passing Year</th><th>DOB</th><th>Actions</th></tr>");

            while (rs.next()) {
                int roll = rs.getInt("roll_no");
                String name = rs.getString("name");
                String branch = rs.getString("branch");
                String gender = rs.getString("gender");
                int year = rs.getInt("passing_year");
                Date dob = rs.getDate("dob");

                out.println("<tr>");
                out.println("<td>" + roll + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + branch + "</td>");
                out.println("<td>" + gender + "</td>");
                out.println("<td>" + year + "</td>");
                out.println("<td>" + dob + "</td>");
                out.println("<td>" +
                        "<button class='update-btn' onclick='updateStudent(" + roll + ")'>Update</button> " +
                        "<button class='delete-btn' onclick='deleteStudent(" + roll + ")'>Delete</button>" +
                        "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
