package com.appmanager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class AppManager
 * 
 */
public class AppManager implements ServletContextListener {

	/**
	 * Default constructor.
	 */

	private static Logger log;

	public AppManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub

		initLog4j(event);

		log.info("initialized context '"
				+ event.getServletContext().getServletContextName() + "'");

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		destroyLog4j(event);
	}

	protected void initLog4j(ServletContextEvent event) {
		// PropertyConfigurator.configureAndWatch
		// (event.getServletContext().getRealPath("/WEB-INF/classes/log4j.properties"));

		PropertyConfigurator.configureAndWatch("/app_mantra/config/log4j.properties");

		log = Logger.getLogger(AppManager.class);
        CachedObjectFactory.start();
		log.info("log4j initialized...");

	}

	/**
	 * Shuts down the logging subsystem.
	 * 
	 * @param event
	 */
	protected void destroyLog4j(ServletContextEvent event) {
		log.error("******* DOWN !!!!! DOWN !!!!! ************" +
				"\n		Server is shutting down \n		Please start server or Nothing would work	\n		If it is planned then ignore message " +
				"\n****************************************************");
        CachedObjectFactory.stop();
		LogManager.shutdown();

	}

}
