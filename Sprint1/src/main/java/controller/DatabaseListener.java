package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import database.DatabaseInfo;

public class DatabaseListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();

		String username = sc.getInitParameter("user_name");
		String password = sc.getInitParameter("password");
		String connectionString = sc.getInitParameter("url")
				+ sc.getInitParameter("database");
		DatabaseInfo.initialize(connectionString,
				username, password);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
