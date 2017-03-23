package com.momforoneday.momforoneday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Caregiver {

    private String _name;
    private String _email;
    private String _telephone;
    private int _age;
    private List<String> _comments;
    private int _rating;
    private Contract _contract;

    public Caregiver(String _name, String _email, String _telephone, int _age) {
        this._name = _name;
        this._email = _email;
        this._telephone = _telephone;
        this._age = _age;
        this._comments = new ArrayList<>();
        this._rating = 0;
    }

    public Contract getContract() {
        return this._contract;
    }

    public void setContract(Contract contract){
        this._contract = contract;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(String _telephone) {
        this._telephone = _telephone;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int _age) {
        this._age = _age;
    }

    public List<String> getComments() {
        return _comments;
    }

    public void setComments(List<String> _comments) {
        this._comments = _comments;
    }

    public int getRating() {
        return _rating;
    }

    public void setRating(int _rating) {
        this._rating = _rating;
    }
}
