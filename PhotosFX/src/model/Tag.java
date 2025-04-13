package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tag implements Serializable{
    private String name;
    private String value;

    public static List<String> tagTypes = new ArrayList<>();
    static {
        tagTypes.add("Location");
        tagTypes.add("Person");
        tagTypes.add("Event");
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tag other = (Tag) obj;
        return name.equalsIgnoreCase(other.name) && value.equalsIgnoreCase(other.value);
    }

    @Override
    public int hashCode() {
        return (name.toLowerCase() + ":" + value.toLowerCase()).hashCode();
    }
}
