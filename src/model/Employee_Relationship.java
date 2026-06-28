package model;
public class Employee_Relationship {
    private int applicant_id;
    private String emp_tin;
    private String emp_type; // Strictly 'Primary', 'Concurrent', or 'Successive'
    private String hire_date;

    // Blank constructor: Let's us make an empty employment link first, before filling it up with user inputs from our UI screens.
    public Employee_Relationship() {}

    // Pre-filled constructor: Let's us make an employment link directly using a taxpayer ID and an employer TIN.
    public Employee_Relationship(int applicant_id, String emp_tin, String emp_type, String hire_date) {
        setApplicant_id(applicant_id);
        setEmp_tin(emp_tin);
        setEmp_type(emp_type);
        setHire_date(hire_date);
    }

    // Getters and Setters (Data Protection): Keeps variables private and controls how we safely read and change them.
    public int getApplicant_id() {
        return applicant_id;
    }

    public final void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getEmp_tin() {
        return emp_tin;
    }

    public final void setEmp_tin(String emp_tin) {
        if (emp_tin == null || !emp_tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Employer TIN must be in 000-000-000-000 format.");
        }
        this.emp_tin = emp_tin;
    }

    public String getEmp_type() {
        return emp_type;
    }

    public final void setEmp_type(String emp_type) {
        if (emp_type == null) {
            throw new IllegalArgumentException("Employment type is required.");
        }
        switch (emp_type) {
            case "Primary":
                this.emp_type = "Primary";
                break;
            case "Concurrent":
                this.emp_type = "Concurrent";
                break;
            case "Successive":
                this.emp_type = "Successive";
                break;
            default:
                throw new IllegalArgumentException("Employment type must be 'Primary', 'Concurrent', or 'Successive'.");
        }
    }

    public String getHire_date() {
        return hire_date;
    }

    public final void setHire_date(String hire_date) {
        if (hire_date == null || !hire_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Hire date must be in YYYY-MM-DD format.");
        }
        this.hire_date = hire_date;
    }
}
