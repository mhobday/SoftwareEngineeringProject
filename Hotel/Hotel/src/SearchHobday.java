import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchHobday")
public class SearchHobday extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchHobday() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionHobday.getDBConnection();
         connection = DBConnectionHobday.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM myTableHobday";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM myTableHobday WHERE LAST_NAME LIKE ?";
            String theEmail = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theEmail);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
        	 String id = rs.getString("ID");
             String first_name = rs.getString("FIRST_NAME");
             String last_name = rs.getString("LAST_NAME");
             String room_number = rs.getString("ROOM_NUMBER");
             String phone = rs.getString("PHONE");
             String email = rs.getString("EMAIL");

            if (keyword.isEmpty() || last_name.contains(keyword)) {
            	response.getWriter().append("USER ID: " + id + ", ");
                response.getWriter().append("FIRST NAME: " + first_name + ", ");
                response.getWriter().append("LAST NAME: " + last_name + ", ");
                response.getWriter().append("ROOM NUMBER: " + room_number + ", ");
                response.getWriter().append("USER PHONE: " + phone + ", ");
                response.getWriter().append("USER EMAIL: " + email + "<br>");
            }
         }
         out.println("<a href=/Hotel/search_hobday.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
