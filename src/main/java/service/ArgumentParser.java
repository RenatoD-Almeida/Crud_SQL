package service;

import Utils.RegisteredFlag;
import Utils.RegisteredOption;

import java.io.IOException;
import java.util.*;

public final class ArgumentParser {
    private final Map<String, Boolean> flagMap = new LinkedHashMap<>();
    private final Map<String, String> optionMap = new LinkedHashMap<>();

    {
        Arrays.stream(RegisteredFlag.values()).forEach( flag -> flagMap.put(flag.toString().toLowerCase(), false));
        Arrays.stream(RegisteredOption.values()).forEach( option -> optionMap.put(option.toString().toLowerCase(), ""));
    }

    private ArgumentParser() {
    }

    public String getOption(RegisteredOption ro)
    {
        return this.optionMap.get(ro.toString().toLowerCase());
    }

    public boolean getFlag(RegisteredFlag rf)
    {
        return this.flagMap.get(rf.toString().toLowerCase());
    }


    public static ArgumentParser Parser(String[] args) throws IOException {

        ArgumentParser argParser = new ArgumentParser();

        Map<String, List<String>> temp = new HashMap<>();
        String argument = null;
        boolean validFlags = false;

        for(var arg : args)
        {
            if(arg.startsWith("--") && !arg.equals("--"))
            {
                argument = arg.substring(2).toLowerCase();

                if (!argParser.flagMap.containsKey(argument))
                    throw new IOException("Flag '" + argument + "' não registrada.");

                if(!(validFlags ^= true))
                    throw new IllegalStateException("Há mais de uma flag ativa");

                argParser.flagMap.put(argument, true);
                continue;
            }

            if(arg.startsWith("-") && !arg.equals("-"))
            {
                argument = arg.substring(1).toLowerCase();

                if(!argParser.optionMap.containsKey(argument))
                    throw new IOException("Option '" + argument + "' não registrada");

                temp.put(argument, new ArrayList<>());
                continue;
            }

            if (argument != null) {
                temp.get(argument).add(arg);
            }
        }

        if(!validFlags)
            throw new IllegalStateException("Não há uma flag ativa");

        temp.forEach((key, value) -> argParser.optionMap.put(key, value.stream().reduce((a, b) -> a.concat(" " + b)).get()));
        return argParser;
    }

    @Override
    public String toString() {
        return "ArgumentParser{" +
                "flagMap : " + flagMap +
                ", optionMap : " + optionMap +
                '}';
    }
}
