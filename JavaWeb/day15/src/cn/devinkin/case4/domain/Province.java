package cn.devinkin.case4.domain;

public class Province {
    private int provinceid;
    private String name;

    public Province() {
        super();
    }

    public Province(int provinceid, String name) {

        this.provinceid = provinceid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceid=" + provinceid +
                ", name='" + name + '\'' +
                '}';
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
