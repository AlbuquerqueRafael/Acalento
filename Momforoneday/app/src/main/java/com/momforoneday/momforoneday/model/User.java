package com.momforoneday.momforoneday.model;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class User {

    private String name;
    private String email;

    public User(){}

    public User(String _name, String _email) {
        this.name = _name;
        this.email = _email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
