package com.momforoneday.momforoneday.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Caregiver {

    private String name;
    private String email;
    private String telephone;
    private int age;
    private List<Comment> comments;
    private List<Integer> schedules;
    private int rating;
    private Contract contract;
    private String course;

    public Caregiver(){
        this.schedules = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Caregiver(String _name, String _email, String _telephone, int _age, List<Integer> schedules, String course,
                Map<String, Notification> notifications) {
        this.name = _name;
        this.email = _email;
        this.telephone = _telephone;
        this.age = _age;
        this.schedules = schedules;
        this.comments = new ArrayList<>();
        this.rating = 0;
        this.course = course;
    }

    public void setCourse(String course){
        this.course = course;
    }


    public String getCourse(){
        return this.course;
    }

    public void setSchedules(List<Integer> schedules) {
        this.schedules = schedules;
    }

    public List<Integer> getSchedules() {
        return this.schedules;
    }

    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract){
        this.contract = contract;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String _telephone) {
        this.telephone = _telephone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int _age) {
        this.age = _age;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> _comments) {
        this.comments = _comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int _rating) {
        this.rating = _rating;
    }
}
