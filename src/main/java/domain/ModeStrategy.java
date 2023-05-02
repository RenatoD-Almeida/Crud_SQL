package domain;

import Utils.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public interface ModeStrategy {

    default void run(String data) throws SQLException {

        try(Connection conn = DBManager.getConnection(data))
        {
            System.out.printf("%n[ %S ]%n", this.getClass().getSimpleName());
            this.runImpl(conn);
        }

    }

    void runImpl(Connection conn) throws SQLException;
}
