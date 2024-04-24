package edu.poly.listener;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class AppListener implements HttpSessionListener, ServletContextListener {

    public AppListener() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    	HttpSession session = se.getSession();
    	ServletContext application = session.getServletContext();
    	Integer visitors = (Integer) application.getAttribute("visitors");
    	application.setAttribute("visitors", visitors+1);
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	ServletContext application = sce.getServletContext();
    	Integer visitors = (Integer) application.getAttribute("visitors");
    	try {
			String path = application.getRealPath("/visitors.txt");
			byte[] data = String.valueOf(visitors).getBytes();
			Files.write(Paths.get(path), data, StandardOpenOption.CREATE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	ServletContext application = sce.getServletContext();
    	Integer visitors = 0;
    	try {
			String path = application.getRealPath("/visitors.txt");
			List<String> lines = Files.readAllLines(Paths.get(path));
			visitors = Integer.parseInt(lines.get(0));
			application.setAttribute("visitors", visitors);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			visitors = 0;
			application.setAttribute("visitors", visitors);
		}
    }
	
}
