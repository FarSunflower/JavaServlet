package listeners;

import utils.FlywayUtil;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Run Flyway migrations when the application starts
        FlywayUtil.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // You can add cleanup code here if needed
    }
}
