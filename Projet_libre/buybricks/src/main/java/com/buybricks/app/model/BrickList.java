package com.buybricks.app.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class BrickList {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="basket_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="brick_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brick brick;

    @Column(name="quantity", length = 500000, nullable = false)
    private long quantity;

    @Column(name="price", length = 2000000, nullable = false)
    private long price;

    // CONSTRUCTOR
    public BrickList(User user, Basket basket, Brick brick, long quantity, long price) {
        if (user != null) {
            this.user = user;
        }
        if (basket != null) {
            this.basket = basket;
        }
        if (brick != null) {
            this.brick = brick;
        }
        this.quantity = quantity;
        this.price = price;
    }

    // GETTERS
    public int getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }

    public Basket getBasket() {
        return this.basket;
    }

    public Brick getBrick() {
        return this.brick;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public long getPrice() {
        return this.price;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    // METHODS
    @Override
    public String toString() {
        String res = "Basket :\n[" + this.basket.toString() + "]\n";
        res += "Brick :\n[" + this.brick.toString() + "]\n";
        res += "Quantity : " + this.quantity + ";\n";
        res += "Price : " + this.price + ";\n";

        return res;
    }

    public boolean equals(BrickList brickList) {
        return (this.user.equals(brickList.getUser()) && this.basket.equals(brickList.getBasket()) && this.brick.equals(brickList.getBrick()) && this.quantity == brickList.getQuantity() && this.price == brickList.getPrice());
    }

    public int hashCode() {
        long hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.user.hashCode();
        hash = hash * 13 + this.basket.hashCode();
        hash = hash * 23 + this.brick.hashCode();
        return (int)hash;
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("id", this.getId());
        res.put("user", this.getUser().buildJson());
        res.put("basket", this.getBasket().buildJson());
        res.put("brick", this.getBrick().buildJson());
        res.put("quantity", this.getQuantity());
        res.put("price", this.getPrice());
        
        return res;
    }

    public static ArrayList<HashMap<String, Object>> buildMultipleJson(Iterable<BrickList> brickLists) {
        ArrayList<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        brickLists.forEach((currentBrickList) -> {
            res.add(currentBrickList.buildJson());
        });

        return res;
    }

    
}
