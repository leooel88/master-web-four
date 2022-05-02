package com.buybricks.app.model;

import java.util.HashMap;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name="product_nb", length = 50, nullable = false)
    private long productNb;

    @Column(name="total_price", length = 5000000, nullable = false)
    private long totalPrice;

    // CONSTRUCTOR
    public Basket(){
        super();
    }
    public Basket(User user) {
        super();
        if (user != null) {
            this.user = user;
        }
        this.productNb = 0;
        this.totalPrice = 0;
    }
    public Basket(User user, long productNb, long totalPrice) {
        super();
        if (user != null) {
            this.user = user;
        }
        this.productNb = productNb;
        this.totalPrice = totalPrice;
    }

    // GETTERS
    public int getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public long getProductNb() {
        return this.productNb;
    }

    public long getTotalPrice() {
        return this.totalPrice;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProductNb(long productNb) {
        this.productNb = productNb;
    }

    // METHODS
    @Override
    public String toString() {
        String res = "User :\n[" + user.toString() + "]\n";
        res += "Product number : " + this.productNb + ";\n";
        res += "Total price : " + this.totalPrice + ";\n";

        return res;
    }

    public int hashCode() {
        long hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.user.hashCode();
        return (int)hash;
    }

    public boolean equals(Basket basket) {
        return (this.user.equals(basket.getUser()) && this.productNb == basket.getProductNb() && this.totalPrice == basket.getTotalPrice());
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("id", this.getId());
        res.put("user", this.getUser().buildJson());
        res.put("productNb", this.getProductNb());
        res.put("totalPrice", this.getTotalPrice());
        return res;
    }
    
}
