package domain;

import Utils.DBManager;
import Utils.Export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public record Consult(String table, String filter, String collumn, Export ex) implements ModeStrategy{

    @Override
    public void runImpl(Connection conn) throws SQLException {

        System.out.println("------------------------------------");
        System.out.println(this.toString());
        System.out.println("------------------------------------");

        String[] splitedTables = table.split(",");

        String query = "SELECT " + (collumn.equals(".") ? "*" : collumn) + " FROM " + splitedTables[0];

        DatabaseMetaData dataBaseMetaData = conn.getMetaData();


        if(splitedTables.length > 1)
        {
            try (ResultSet exportedKeys = dataBaseMetaData.getExportedKeys(null, null, splitedTables[0])) {

                while(exportedKeys.next())
                {
                    for(int i = 1; i < splitedTables.length; ++i)
                    {
                        if(splitedTables[i].equalsIgnoreCase(exportedKeys.getString("FKTABLE_NAME")))
                        {
                            query += " INNER JOIN %s ".formatted(splitedTables[i]);
                            query += "ON %s = %s".formatted(exportedKeys.getString("PKCOLUMN_NAME"),exportedKeys.getString("FKCOLUMN_NAME"));
                        }
                    }
                }
            }
        }

        if(!filter.equals("."))
            query += " WHERE " + filter;

        System.out.println(query);

        try(PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery())
        {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            StringBuilder collumns = new StringBuilder();

            for(int i = 1; i <= columnCount; ++i)
            {
                collumns.append("%-30S".formatted(resultSetMetaData.getColumnName(i)));
            }

            int len = collumns.length() - 24;
            collumns.append('\n'+ "-".repeat(len));
            System.out.println(collumns);

            StringBuilder collumnsValue = new StringBuilder();

            while(resultSet.next())
            {
                for(int i = 1; i <= columnCount; ++i)
                {
                    Object value = resultSet.getObject(i);
                    collumnsValue.append("%-30s".formatted(value));
                }
                collumnsValue.append('\n');
            }
            collumnsValue.append("-".repeat(len));
            System.out.println(collumnsValue);


            if(ex == Export.ON)
            {
                try(BufferedWriter writer = new BufferedWriter(new FileWriter("./QuerySaved.txt", true)))
                {
                    writer.write(query);
                    writer.newLine();
                    writer.newLine();
                    writer.write(collumns.toString());
                    writer.newLine();
                    writer.write(collumnsValue.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Consult{" +
                "table='" + table + '\'' +
                ", filter='" + filter + '\'' +
                ", collumn='" + collumn + '\'' +
                ", ex=" + ex +
                '}';
    }
}
