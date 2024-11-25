package utils;

import org.flywaydb.core.Flyway;

public class FlywayUtil {

    public static void migrate() {
        try {
            // Connect to the PostgreSQL Docker container
            String url = "jdbc:postgresql://localhost:5572/custom";  // Host and port from Docker
            String user = "postgres";  // User from environment variable
            String password = "123";  // Password from environment variable

            // Create Flyway instance
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration")  // The folder where your migration scripts are
                    .load();

            // Start the migration
            flyway.migrate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Flyway migration failed", e);
        }
    }
}
