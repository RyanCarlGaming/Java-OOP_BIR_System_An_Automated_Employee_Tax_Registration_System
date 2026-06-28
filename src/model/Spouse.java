package model;
public class Spouse {
    private int applicant_id;
    private String spouse_fullname;
    private String spouse_employment_status;
    private String exemption_claimant;
    private String spouse_emp_tin;
    private String spouse_tin;

    // Blank constructor: Let's us make an empty spouse object first, before filling it up with user inputs from our UI screens.
    public Spouse() {}

    // Pre-filled constructor: Let's us make a spouse with their details set immediately, linking them to the main taxpayer.
    public Spouse(int applicant_id, String spouse_fullname, String spouse_employment_status, String exemption_claimant, String spouse_emp_tin, String spouse_tin) {
        setApplicant_id(applicant_id);
        setSpouse_fullname(spouse_fullname);
        setSpouse_employment_status(spouse_employment_status);
        setExemption_claimant(exemption_claimant);
        setSpouse_emp_tin(spouse_emp_tin);
        setSpouse_tin(spouse_tin);
    }

    // Getters and Setters (Data Protection): Keeps variables private and controls how we safely read and change them.
    public int getApplicant_id() {
        return applicant_id;
    }

    public final void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getSpouse_fullname() {
        return spouse_fullname;
    }

    public final void setSpouse_fullname(String spouse_fullname) {
        if (spouse_fullname == null || spouse_fullname.isEmpty()) {
            throw new IllegalArgumentException("Spouse full name is required.");
        }
        this.spouse_fullname = spouse_fullname;
    }

    public String getSpouse_employment_status() {
        return spouse_employment_status;
    }

    public final void setSpouse_employment_status(String spouse_employment_status) {
        if (spouse_employment_status == null) {
            throw new IllegalArgumentException("Spouse employment status is required.");
        }
        java.util.List<String> validStates = java.util.Arrays.asList(
            "Unemployed", "Employed Locally", "Employed Abroad", "Engaged in Business/Practice of Profession"
        );
        if (!validStates.contains(spouse_employment_status)) {
            throw new IllegalArgumentException("Invalid Spouse Employment Status. Must be Unemployed, Employed Locally, Employed Abroad, or Engaged in Business/Practice of Profession.");
        }
        this.spouse_employment_status = spouse_employment_status;
    }

    public String getExemption_claimant() {
        return exemption_claimant;
    }

    public final void setExemption_claimant(String exemption_claimant) {
        if (exemption_claimant == null || exemption_claimant.isEmpty()) {
            this.exemption_claimant = null;
            return;
        }
        switch (exemption_claimant) {
            case "Husband Claims":
                this.exemption_claimant = "Husband Claims";
                break;
            case "Wife Claims":
                this.exemption_claimant = "Wife Claims";
                break;
            default:
                throw new IllegalArgumentException("Exemption claimant must be 'Husband Claims' or 'Wife Claims'.");
        }
    }

    public String getSpouse_emp_tin() {
        return spouse_emp_tin;
    }

    public final void setSpouse_emp_tin(String spouse_emp_tin) {
        if (spouse_emp_tin != null && !spouse_emp_tin.isEmpty()) {
            if (!spouse_emp_tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
                throw new IllegalArgumentException("Spouse Employer TIN must be in 000-000-000-000 format.");
            }
            this.spouse_emp_tin = spouse_emp_tin;
        } else {
            this.spouse_emp_tin = null;
        }
    }

    public String getSpouse_tin() {
        return spouse_tin;
    }

    public final void setSpouse_tin(String spouse_tin) {
        if (spouse_tin == null || !spouse_tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Spouse TIN must be in 000-000-000-000 format.");
        }
        this.spouse_tin = spouse_tin;
    }
}