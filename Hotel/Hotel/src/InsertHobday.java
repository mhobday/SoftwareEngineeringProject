
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertHobday")
public class InsertHobday extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertHobday() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String first = request.getParameter("first_name");
      String last = request.getParameter("last_name");
      String room = request.getParameter("room_number");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");


      Connection connection = null;
      String insertSql = " INSERT INTO myTableHobday (id, FIRST_NAME, LAST_NAME, ROOM_NUMBER, PHONE, EMAIL) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionHobday.getDBConnection();
         connection = DBConnectionHobday.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, first);
         preparedStmt.setString(2, last);
         preparedStmt.setString(3, room);
         preparedStmt.setString(4, phone);
         preparedStmt.setString(5, email);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>First Name</b>: " + first + "\n" + //
            "  <li><b>Last Name</b>: " + last + "\n" + //
            "  <li><b>Room Number</b>: " + room + "\n" + //
            "  <li><b>Phone</b>: " + phone + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "</ul>\n");

      out.println("<a href=/Hotel/search_hobday.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
