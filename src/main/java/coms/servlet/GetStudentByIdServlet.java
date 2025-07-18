package coms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getStudentById")
public class GetStudentByIdServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Saurabh@123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int rollNo = Integer.parseInt(req.getParameter("rollNo"));
        res.setContentType("application/json");
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE roll_no = ?")) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, rollNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String json = String.format(
                    "{ \"roll_no\": %d, \"name\": \"%s\", \"branch\": \"%s\", \"gender\": \"%s\", \"passing_year\": %d, \"dob\": \"%s\" }",
                    rs.getInt("roll_no"),
                    rs.getString("name"),
                    rs.getString("branch"),
                    rs.getString("gender"),
                    rs.getInt("passing_year"),
                    rs.getDate("dob").toString()
                );
                res.getWriter().write(json);
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
