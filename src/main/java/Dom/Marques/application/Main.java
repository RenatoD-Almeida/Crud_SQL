package Dom.Marques.application;

import service.ArgumentParser;
import service.ModeFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {


        try(InputStream is = new FileInputStream("./src/main/resources/dbconfig.properties"))
        {
            Properties props = new Properties();
            props.load(is);

            ArgumentParser argParser = ArgumentParser.Parser(args);

            Objects.requireNonNull(ModeFactory.get(argParser)).run(props);

        }catch (RuntimeException | IOException | SQLException err )
        {
            System.out.println(err.getMessage());
        }

    }
}