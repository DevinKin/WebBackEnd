package cn.devinkin.case4.domain;

public class City {
    private int cityid;
    private int provinceid;
    private String name;

    @Override
    public String toString() {
        return "City{" +
                "cityid=" + cityid +
                ", provinceid=" + provinceid +
                ", name='" + name + '\'' +
                '}';
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
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
