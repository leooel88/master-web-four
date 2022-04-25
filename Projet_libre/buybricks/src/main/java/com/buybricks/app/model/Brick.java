package com.buybricks.app.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.*;

@Entity
@Table(name = "brick")
public class Brick {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name="dim_h", length = 100, nullable = false)
    private long dimH;

    @Column(name="dim_l", length = 100, nullable = false)
    private long dimL;

    @Column(name="dim_w", length = 100, nullable = false)
    private long dimW;

    @Column(name="price", length = 20000, nullable = false)
    private long price;

    @Column(name="quantity", length = 500000, nullable = false)
    private long quantity;

    @Column(name="imageUrl", length = 512, nullable = false)
    private String imageUrl;

    // CONSTRUCTOR
    public Brick(String name, long dimH, long dimL, long dimW, long price, long quantity, String imageUrl) {
        super();
        this.name = name;
        this.dimH = dimH;
        this.dimL = dimL;
        this.dimW = dimW;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // GETTERS
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getDimH() {
        return this.dimH;
    }

    public long getDimL() {
        return this.dimL;
    }

    public long getDimW() {
        return this.dimW;
    }

    public long getPrice() {
        return this.price;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDimH(long dimH) {
        this.dimH = dimH;
    }

    public void setDimL(long dimL) {
        this.dimL = dimL;
    }

    public void setDimW(long dimW) {
        this.dimW = dimW;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // METHODS
    @Override
    public String toString() {
        String res =  "Name : " + this.name + ";\n";
        res += "DimH : " + this.dimH + ";\n";
        res += "DimL : " + this.dimL + ";\n";
        res += "DimW : " + this.dimW + ";\n";
        res += "Price : " + this.price + ";\n";
        res += "Quantity : " + this.quantity + ";\n";
        res += "ImageUrl : " + this.imageUrl + ";\n";
        return res;
    }

    public boolean equals(Brick brick) {
        return (this.name.equals(brick.getName()) && this.dimH == brick.getDimH() && this.dimL == brick.getDimL() && this.dimW == brick.getDimW() && this.price == brick.getPrice() && this.quantity == brick.getQuantity() && this.imageUrl.equals(brick.getImageUrl()));
    }

    public int hashCode() {
        long hash = 1;
        hash = hash * 17 + this.id;
        hash = hash * 31 + this.name.hashCode();
        hash = hash * 13 + this.imageUrl.hashCode();
        return (int)hash;
    }

    public HashMap<String, Object> buildJson() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("id", this.getId());
        res.put("name", this.getName());
        res.put("dimH", this.getDimH());
        res.put("dimL", this.getDimL());
        res.put("dimW", this.getDimW());
        res.put("price", this.getPrice());
        res.put("quantity", this.getQuantity());
        res.put("imageUrl", this.getImageUrl());
        return res;
    }

    public static ArrayList<HashMap<String, Object>> buildMultipleJson(Iterable<Brick> bricks) {
        ArrayList<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        bricks.forEach((currentBrick) -> {
            res.add(currentBrick.buildJson());
        });

        return res;
    }
}
