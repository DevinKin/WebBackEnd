package cn.devinkin.pro1.domain;

public class Product {
    private int id;
    private String pname;
    private String price;
    private String pdsc;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", price='" + price + '\'' +
                ", pdsc='" + pdsc + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPdsc() {
        return pdsc;
    }

    public void setPdsc(String pdsc) {
        this.pdsc = pdsc;
    }
}
