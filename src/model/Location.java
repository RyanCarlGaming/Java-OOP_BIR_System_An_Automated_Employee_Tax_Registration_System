package model;
public class Location {
    private String mun_code;
    private String mun;
    private String rdo_code;

    // Pre-filled constructor: Let's us make a location using information queried from the database checking.
    public Location(String mun_code, String mun, String rdo_code) {
        this.mun_code = mun_code;
        this.mun = mun;
        this.rdo_code = rdo_code;
    }

    // Getters and Setters (Data Protection): Keeps variables private and controls how we safely read and change them.
    public String getMun_code() {
        return mun_code;
    }

    public void setMun_code(String mun_code) {
        this.mun_code = mun_code;
    }

    public String getMun() {
        return mun;
    }

    public void setMun(String mun) {
        this.mun = mun;
    }

    public String getRdo_code() {
        return rdo_code;
    }

    public void setRdo_code(String rdo_code) {
        this.rdo_code = rdo_code;
    }

    // Text Display: Changes how this object looks as text so it displays nicely inside a dropdown list.
    public String toString() {
        return mun + " (" + mun_code + ")";
    }
}