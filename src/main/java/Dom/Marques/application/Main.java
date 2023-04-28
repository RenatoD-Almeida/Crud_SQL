package Dom.Marques.application;

import Utils.RegisteredOption;
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
            System.out.println(argParser);

            Objects.requireNonNull(ModeFactory.get(argParser))
                    .run(argParser.getOption(RegisteredOption.DB));

        }catch (RuntimeException | IOException | SQLException err )
        {
            System.out.println(err.getMessage());
        }

    }
}