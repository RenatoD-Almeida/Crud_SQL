package domain;

import Utils.DB;

import java.sql.Connection;
import java.sql.SQLException;

public interface ModeStrategy {

    default void run() throws SQLException {

        try(Connection conn = DB.getConnection())
        {
            System.out.printf("%n[ %S ]%n", this.getClass().getSimpleName());
            this.runImpl(conn);
        }

    }

    void runImpl(Connection conn) throws SQLException;
}
