package DOMAIN;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by santi on 1/9/2017.
 */

public class Product {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String nameProduct;
    @SerializedName("category")
    @Expose
    private String categoryProduct;
    @SerializedName("price")
    @Expose
    private int priceProduct;
    @SerializedName("quantity")
    @Expose
    private int amountProduct;
    @SerializedName("status")
    @Expose
    private String stateProduct;
    @SerializedName("path")
    @Expose
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(int amountProduct) {
        this.amountProduct = amountProduct;
    }

    public String getStateProduct() {
        return stateProduct;
    }

    public void setStateProduct(String stateProduct) {
        this.stateProduct = stateProduct;
    }
}//class
