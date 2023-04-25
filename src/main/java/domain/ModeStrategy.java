package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public interface ModeStrategy {

    default void run(Properties props) throws SQLException {

        try(Connection conn = DriverManager.getConnection(props.getProperty("url"), props))
        {
            System.out.printf("[ %S ]%n", this.getClass().getSimpleName());
            this.runImpl(conn);
        }

    }

    void runImpl(Connection conn) throws SQLException;
}
