package org.nishikant;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Wrapper;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        //bootstraps HTTP Engine of Tomcat
        tomcat.getConnector();

        /*A single web server can have multiple web application each with different set of servlets
        * contextPath - lets you specify for which web application out of those many you want to build context
        * docBase - if you have any static data to serve, path to that*/
        Context ctx = tomcat.addContext("", null);

        //telling server which servlet to reach out to, for serving content, when request comes to given mapping.
        Wrapper servlet = Tomcat.addServlet(ctx, "myFirstServlet", new MyFirstServlet());
        //loads the servlet immediately, NOT on first request
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }
}