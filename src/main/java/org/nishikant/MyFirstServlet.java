package org.nishikant;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Servlet - A java class which can generated dynamic content
* to serve to online user.*/
public class MyFirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Can be application/json or application/xml as well*/
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(
                """
                        <html>
                        <body>
                        <h1>Hello World</h1>
                        <p>This is my very first, embedded Tomcat, HTML Page!</p>
                        </body>
                        </html>""");
    }
}
