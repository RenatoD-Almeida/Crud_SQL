package domain;

import java.sql.*;

public record Consult(String table, String filter, String collumn) implements ModeStrategy{

    @Override
    public void runImpl(Connection conn) throws SQLException {

        System.out.println("------------------------------------");
        System.out.println(this.toString());
        System.out.println("------------------------------------");

        String query = "SELECT " + collumn + " FROM " + table;

        boolean anything = filter.equals("*");

        if(!anything)
            query += " WHERE " + filter;

        try(PreparedStatement ps = conn.prepareStatement(query))
        {

            try(ResultSet resultSet = ps.executeQuery())
            {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                StringBuilder collumns = new StringBuilder();

                for(int i = 1; i <= columnCount; ++i)
                {
                    collumns.append("%-30S".formatted(metaData.getColumnName(i)));
                }

                System.out.println(collumns);
                System.out.println("-".repeat(collumns.length() - 24));

                while(resultSet.next())
                {
                    for(int i = 1; i <= columnCount; ++i)
                    {
                        Object value = resultSet.getObject(i);
                        System.out.printf("%-30s", value);
                    }
                    System.out.println();
                }
            }
        }

    }

    @Override
    public String toString() {
        return "Table : " + table + "\nFilter : " + filter + "\nCollun : " + collumn;
    }
}
