package model;

//Made the person class abstract to be a blueprint for its subclasses
//Person is an abstract class that doesn't allow it to be instantiated directly
public abstract class Person { 
    private String taxpayer_fullname;
    private String gender;
    private String civil_status;
    private String date_of_birth;
    private String place_of_birth;

    // Blank constructor: Let's us make an empty Person object first, without having to type their details right away.
    public Person() {}

    // Pre-filled constructor: Let's us make a Person with their details set immediately in one step.
    public Person(String taxpayer_fullname, String gender, String civil_status, String date_of_birth, String place_of_birth) {
        this.taxpayer_fullname = taxpayer_fullname;
        this.gender = gender;
        this.civil_status = civil_status;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
    }

    // Getters and Setters (Data Protection): Keeps variables private and controls how we safely read and change them.
    public String getTaxpayer_fullname() {
        return taxpayer_fullname;
    }

    public final void setTaxpayer_fullname(String taxpayer_fullname) {
        if (taxpayer_fullname == null || taxpayer_fullname.isEmpty()) {
            throw new IllegalArgumentException("Taxpayer full name is required.");
        }
        this.taxpayer_fullname = taxpayer_fullname;
    }

    public String getGender() {
        return gender;
    }

    public final void setGender(String gender) {
        if (gender == null || (!gender.equals("Male") && !gender.equals("Female"))) {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'.");
        }
        this.gender = gender;
    }

    public String getCivil_status() {
        return civil_status;
    }

    public final void setCivil_status(String civil_status) {
        if (civil_status == null) {
            throw new IllegalArgumentException("Civil status is required.");
        }
        java.util.List<String> validStatuses = java.util.Arrays.asList(
            "Single", "Married", "Widow/er", "Legally Separated", "With Qualified Dependent Child/ren"
        );
        if (!validStatuses.contains(civil_status)) {
            throw new IllegalArgumentException("Invalid Civil Status. Must be Single, Married, Widow/er, Legally Separated, or With Qualified Dependent Child/ren.");
        }
        this.civil_status = civil_status;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public final void setDate_of_birth(String date_of_birth) {
        if (date_of_birth == null || !date_of_birth.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date of Birth must be in YYYY-MM-DD format.");
        }
        this.date_of_birth = date_of_birth;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public final void setPlace_of_birth(String place_of_birth) {
        if (place_of_birth == null || place_of_birth.isEmpty()) {
            throw new IllegalArgumentException("Place of Birth is required.");
        }
        this.place_of_birth = place_of_birth;

    }

    //Create an abstract getSummary method that forces subclasses to make
    //their own implementation of getSummary in their end
    public abstract String getSummary();
}