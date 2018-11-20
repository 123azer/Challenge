package com.example.maximebritto.addressbook.data;

/**
 * Created by trainermac on 02/03/16.
 */
public class Group {
    int id;
    String name;
    String description;
    Owner owner;
    Integer stargazers_count;

    public Owner getOwner() {
        return owner;
    }
    public String getRepositoryName(){return name;}
    public  String getDescription(){return description;}
    public Integer getNumberStars(){return stargazers_count;}
}
