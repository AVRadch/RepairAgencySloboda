package com.my.repairagency007;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
 //   private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/library?user = root & password = 1111" ;
    private String message;

    public void init() {
        message = "Hello World!!!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
 /*       try {
            DataSource ds = null;
   //         Class.forName("com.mysql.cj.jdbc.Driver");
  //          Connection con = DriverManager.getConnection(CONNECTION_URL);
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("TestDB");
            Connection con = ds.getConnection();
            System.out.println(con);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from books");
            while (rs.next()){
                System.out.println(rs.getString(2));
            }
            con.close();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
*/
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}