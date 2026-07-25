package com.back;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Rq {
    private final String cmd;
    private final Map<String, String> params;

    public Rq(String cmd) {
        this.cmd = cmd;
        String[] cmdBits = cmd.split("\\?", 2);

        String queryString = cmdBits.length == 2 ? cmdBits[1] : "";

        params = Arrays
                .stream(queryString.split("&"))
                .map(e -> e.split("=", 2))
                .filter(e -> e.length == 2 && !e[0].isBlank() && !e[1].isBlank())
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    public String getActionName() {
        return cmd.split("\\?", 2)[0];
    }

    public String getParam(String name, String defaultValue) {
        return params.getOrDefault(name, defaultValue);
    }

    public int getParamAsInt(String name, int defaultValue) {
        String value = getParam(name, "");

        if (value.isBlank()) return defaultValue;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
