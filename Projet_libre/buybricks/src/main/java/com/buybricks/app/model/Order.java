package com.buybricks.app.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name="product_nb", length = 50, nullable = false)
    private long productNb;

    @Column(name="total_price", length = 5000000, nullable = false)
    private long totalPrice;

    @Column(name="received", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean received;

    // CONSTRUCTOR
    public Order(){
        super();
    }
    public Order(User user, long productNb, long totalPrice) {
        super();
        if (user != null) {
            this.user = user;
        }
        this.productNb = productNb;
        this.totalPrice = totalPrice;
        this.received = true;
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

    public boolean getReceived() {
        return this.received;
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

    public void setReceived(boolean received) {
        this.received = received;
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

    public boolean equals(Order order) {
        return (this.user.equals(order.getUser()) && this.productNb == order.getProductNb() && this.totalPrice == order.getTotalPrice() && this.received == order.getReceived());
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("id", this.getId());
        res.put("user", this.getUser().buildJson());
        res.put("productNb", this.getProductNb());
        res.put("totalPrice", this.getTotalPrice());
        res.put("received", this.getReceived());
        return res;
    }

    public static ArrayList<HashMap<String, Object>> buildMultipleJson(Iterable<Order> orders) {
        ArrayList<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        orders.forEach((currentOrder) -> {
            res.add(currentOrder.buildJson());
        });

        return res;
    }
}
