package model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private String name;
    private String value;

    public static List<String> tagTypes = new ArrayList<>();

    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String toString()
    {
        return name + ": " + value;
    }
}
