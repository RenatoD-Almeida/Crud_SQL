package Dom.Marques.application;

import service.ArgumentParser;
import service.ModeFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {


        try
        {
            ArgumentParser argParser = ArgumentParser.Parser(args);
            Objects.requireNonNull(ModeFactory.get(argParser)).run();

        }catch (RuntimeException | IOException | SQLException err )
        {
            System.out.println(err.getMessage());
        }

    }
}