package com.quest.etna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="street", length = 100, nullable = false)
    private String street;

    @Column(name="postal_code", length = 30, nullable = false)
    private String postalCode;

    @Column(name="city", length = 50, nullable = false)
    private String city;

    @Column(name="country", length = 50, nullable = false)
    private String country;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="updated_date")
    private Date updatedDate;

    public Address() {
        super();
    }

    public Address(String street, String postalCode, String city, String country, User user) {
        super();
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        if (user != null) {
            this.user = user;
        }
        this.creationDate = new Date();
        this.updatedDate = new Date();
    }

    public int getId() {
        return this.id;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public User getUser() {
        return this.user;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Date getUpdateDate() {
        return this.updatedDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Address[street = " + this.street + ", postalCode = " + this.postalCode + ", city = " + this.city + ", country = " + this.country + ", user = " + this.user + "]";
    }

    public boolean equals(Address address) {
        return (this.street.equals(address.getStreet()) && this.postalCode.equals(address.getPostalCode()) && this.city.equals(address.getCity()) && this.country.equals(address.getCountry()));
    }

    public int hashCode() {
        long hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.street.hashCode();
        hash = hash * 13 + this.postalCode.hashCode();
        return (int)hash;
    }

    public HashMap<String, String> buildJson() {
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("street", this.getStreet());
        res.put("postalCode", this.getPostalCode());
        res.put("city", this.getCity());
        res.put("country", this.getCountry());
        return res;
    }

    public static ArrayList<HashMap<String, String>> buildMultipleJson(Iterable<Address> addresses) {
        ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
        addresses.forEach((currentAddress) -> {
            res.add(currentAddress.buildJson());
        });

        return res;
    }

}
