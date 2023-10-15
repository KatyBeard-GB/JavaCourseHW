package seminar6.homework;

public class Laptops {
    private String ram;
    private int hdd;
    private String os;
    private String color;

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public int getHdd() {
        return hdd;
    }

    public void setHdd(int hdd) {
        this.hdd = hdd;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Laptops{" +
                "ОЗУ = '" + ram + '\'' +
                ", Объем ЖД = " + hdd +
                ", Операционная система = '" + os + '\'' +
                ", Цвет = '" + color + '\'' +
                '}';
    }
}
