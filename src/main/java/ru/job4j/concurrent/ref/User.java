package ru.job4j.concurrent.ref;

public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }
}
