package model;
public class Employer {
    private String emp_tin;
    private String emp_fullname;
    private String emp_full_address;
    private String zip_code;
    private String emp_landline;
    private String emp_mun_code;
    private String registering_office_type;

    // Blank constructor: Let's us make an empty employer object first, before filling it up with user inputs from our UI screens.
    public Employer() {}

    // Pre-filled constructor: Let's us make an employer with all company details set immediately in one step.
    public Employer(String emp_tin, String emp_fullname, String emp_full_address, String zip_code, String emp_landline, String emp_mun_code, String registering_office_type) {
        setEmp_tin(emp_tin);
        setEmp_fullname(emp_fullname);
        setEmp_full_address(emp_full_address);
        setZip_code(zip_code);
        setEmp_landline(emp_landline);
        setEmp_mun_code(emp_mun_code);
        setRegistering_office_type(registering_office_type);
    }

    // Getters and Setters (Data Protection): Keeps variables private and controls how we safely read and change them.
    public String getEmp_tin() {
        return emp_tin;
    }

    public final void setEmp_tin(String emp_tin) {
        if (emp_tin == null || !emp_tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Employer TIN must be in 000-000-000-000 format.");
        }
        this.emp_tin = emp_tin;
    }

    public String getEmp_fullname() {
        return emp_fullname;
    }

    public final void setEmp_fullname(String emp_fullname) {
        if (emp_fullname == null || emp_fullname.isEmpty()) {
            throw new IllegalArgumentException("Employer full name is required.");
        }
        this.emp_fullname = emp_fullname;
    }

    public String getEmp_full_address() {
        return emp_full_address;
    }

    public final void setEmp_full_address(String emp_full_address) {
        this.emp_full_address = emp_full_address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public final void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getEmp_landline() {
        return emp_landline;
    }

    public final void setEmp_landline(String emp_landline) {
        this.emp_landline = emp_landline;
    }

    public String getEmp_mun_code() {
        return emp_mun_code;
    }

    public final void setEmp_mun_code(String emp_mun_code) {
        this.emp_mun_code = emp_mun_code;
    }

    public String getRegistering_office_type() {
        return registering_office_type;
    }

    public final void setRegistering_office_type(String registering_office_type) {
        this.registering_office_type = registering_office_type;
    }
}