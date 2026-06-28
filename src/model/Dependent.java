package model;
public class Dependent {
    private int DependentId;
    private int applicant_id;
    private String dependent_fullname;
    private String dependent_dob;
    private String is_incapacitated; // "Yes" or "No"

    // Default constructor
    public Dependent() {}

    // Parameterized constructor including dependent_id
    public Dependent(int DependentId, int applicant_id, String dependent_fullname, String dependent_dob, String is_incapacitated) {
        this.DependentId = DependentId;
        this.applicant_id = applicant_id;
        setDependent_fullname(dependent_fullname);
        setIs_incapacitated(is_incapacitated);
        setDependent_dob(dependent_dob);
    }

    // Parameterized constructor excluding dependent_id (useful for new records before saving)
    public Dependent(int applicant_id, String dependent_fullname, String dependent_dob, String is_incapacitated) {
        this.applicant_id = applicant_id;
        setDependent_fullname(dependent_fullname);
        setIs_incapacitated(is_incapacitated);
        setDependent_dob(dependent_dob);
    }

    // Getters and Setters
    public int getDependent_id() {
        return DependentId;
    }

    public void setDependent_id(int DependentId) {
        this.DependentId = DependentId;
    }

    public int getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getDependent_fullname() {
        return dependent_fullname;
    }

    public final void setDependent_fullname(String dependent_fullname) {
        if (dependent_fullname == null || dependent_fullname.isEmpty()) {
            throw new IllegalArgumentException("Dependent full name is required.");
        }
        this.dependent_fullname = dependent_fullname;
    }

    public String getDependent_dob() {
        return dependent_dob;
    }

    public final void setDependent_dob(String dependent_dob) {
        if (dependent_dob == null || !dependent_dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Dependent Date of Birth must be in YYYY-MM-DD format.");
        }
        int birthYear = Integer.parseInt(dependent_dob.substring(0, 4));
        int age = 2026 - birthYear;
        if (age > 21 && "No".equals(this.is_incapacitated)) {
            throw new IllegalArgumentException("Dependent cannot be over 21 unless they are incapacitated.");
        }
        this.dependent_dob = dependent_dob;
    }

    public String getIs_incapacitated() {
        return is_incapacitated;
    }

    
    public void setIs_incapacitated(String is_incapacitated) {
        if (is_incapacitated != null && is_incapacitated.equalsIgnoreCase("Yes")){
        this.is_incapacitated = "Yes";
        }else{
        this.is_incapacitated = "No";
        }


}
}

