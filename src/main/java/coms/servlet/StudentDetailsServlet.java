package coms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/studentForm")
public class StudentDetailsServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Saurabh@123"; 

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	
    	
    	
        String name = req.getParameter("name");
        String branch = req.getParameter("branch").trim();
        String gender = req.getParameter("gender");
        String passingYear = req.getParameter("passingYear");
        String dob = req.getParameter("dob");
        
//        System.out.println(">>> Received:");
//        System.out.println("Name: " + name);
//        System.out.println("Branch: " + branch);
//        System.out.println("Gender: " + gender);
//        System.out.println("Passing Year: " + passingYear);
//        System.out.println("DOB: " + dob);
//
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String sql = "INSERT INTO students (name, branch, gender, passing_year, dob) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);
            ps.setString(2, branch);
            ps.setString(3, gender);
            ps.setInt(4, Integer.parseInt(passingYear));
            ps.setDate(5, Date.valueOf(dob));

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int rollNo = rs.getInt(1);
                    
                    res.sendRedirect("SDSuccessfull.html");
                }
            } else {
                out.println("<h2>Failed to save student data. Please try again.</h2>" );
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
