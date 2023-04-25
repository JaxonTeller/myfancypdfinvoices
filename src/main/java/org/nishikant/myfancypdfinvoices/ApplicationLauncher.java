package org.nishikant.myfancypdfinvoices;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Wrapper;
import org.nishikant.config.ApplicationConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

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
        WebApplicationContext appCtx = createApplicationContext(ctx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
        Wrapper servlet = Tomcat.addServlet(ctx, "dispatcherServlet", dispatcherServlet);
        //loads the servlet immediately, NOT on first request
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    /*Your controllers which handles HTTP request will not work, because your tomcat is still working on old HTTPServlet,
    * so you need a different servlet, which could dispatch HTTP request to Controller and response from Controller
    * back to client, this is done by a servlet called DispatcherServlet
    * But how do you make this DispatcherServlet aware of your Controller classes ?
    * -> for that it needs to be aware of ApplicationContext, and ApplicationContext needs the Configuration
    * which provides all the beans, via bean definitions or ComponentScans,
    * it means indirectly your DispatcherServlet knows about Controller classes.*/
    public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }
}