import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB")
public class MyServletDBHobday extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-18-222-6-224.us-east-2.compute.amazonaws.com:3306/myDB";
   static String user = "babyboiremote";
   static String password = "babyboi";
   static Connection connection = null;

   public MyServletDBHobday() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); //old:com.mysql.jdbc.Driver
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM myTableHobday WHERE LAST_NAME LIKE ?";
         String theUserName = "last_name%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
         preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            String id = rs.getString("ID");
            String first_name = rs.getString("FIRST_NAME");
            String last_name = rs.getString("LAST_NAME");
            String room_number = rs.getString("ROOM_NUMBER");
            String phone = rs.getString("PHONE");
            String email = rs.getString("EMAIL");
            response.getWriter().append("USER ID: " + id + ", ");
            response.getWriter().append("FIRST NAME: " + first_name + ", ");
            response.getWriter().append("LAST NAME: " + last_name + ", ");
            response.getWriter().append("ROOM NUMBER: " + room_number + ", ");
            response.getWriter().append("USER PHONE: " + phone + ", ");
            response.getWriter().append("USER EMAIL: " + email + "<br>");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
