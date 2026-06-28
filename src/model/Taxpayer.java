package model;
// extends Person: This means Taxpayer inherits (shares) all details from the Person class so we don't have to rewrite code for name, gender, etc.

public class Taxpayer extends Person {
    private int applicant_id;
    private String taxpayer_tin;
    private String bir_reg_date;
    private String pcn;
    private String taxpayer_type;
    private String citizenship;
    private String other_citizenship;
    private String mother_fullname;
    private String father_fullname;
    private String full_address;
    private String foreign_address;
    private String mun_code;
    private String zip_code;
    private String landline;
    private String fax;
    private String mobile;
    private String email;
    private String tax_type;
    private String form_type;
    private String atc;
    private String id_type;
    private String id_number;
    private String id_effectivity;
    private String id_expiry; 
    private String id_issuer;
    private String id_place;

    // Blank constructor: Let's us make an empty Taxpayer object first, before filling it up with user inputs from our UI screens.
    public Taxpayer() {
        super();
    }

    // Pre-filled constructor WITH ID: Used when loading a taxpayer that is already saved in the database.
    public Taxpayer(int applicant_id, String taxpayer_tin, String bir_reg_date, String pcn, String taxpayer_type, String taxpayer_fullname, String gender, String civil_status, String date_of_birth, String place_of_birth, String citizenship, String other_citizenship, String mother_fullname, String father_fullname, String full_address, String foreign_address, String mun_code, String zip_code, String landline, String fax, String mobile, String email, String tax_type, String form_type, String atc, String id_type, String id_number, String id_effectivity, String id_expiry, String id_issuer, String id_place) {
        // super(): Sends personal details to the Person class so we can reuse its code.
        super(taxpayer_fullname, gender, civil_status, date_of_birth, place_of_birth);
        setApplicant_id(applicant_id);
        setCitizenship(citizenship); // Set citizenship first to avoid foreign address checks failing
        setForeign_address(foreign_address);
        setTaxpayer_tin(taxpayer_tin);
        setBir_reg_date(bir_reg_date);
        setPcn(pcn);
        setTaxpayer_type(taxpayer_type);
        setOther_citizenship(other_citizenship);
        setMother_fullname(mother_fullname);
        setFather_fullname(father_fullname);
        setFull_address(full_address);
        setMun_code(mun_code);
        setZip_code(zip_code);
        setLandline(landline);
        setFax(fax);
        setMobile(mobile);
        setEmail(email);
        setTax_type(tax_type);
        setForm_type(form_type);
        setAtc(atc);
        setId_type(id_type);
        setId_number(id_number);
        setId_effectivity(id_effectivity);
        setId_expiry(id_expiry);
        setId_issuer(id_issuer);
        setId_place(id_place);
    }

    // Pre-filled constructor WITHOUT ID: Used when creating a new taxpayer (the database will make an ID for them).
    public Taxpayer(String taxpayer_tin, String bir_reg_date, String pcn, String taxpayer_type, String taxpayer_fullname, String gender, String civil_status, String date_of_birth, String place_of_birth, String citizenship, String other_citizenship, String mother_fullname, String father_fullname, String full_address, String foreign_address, String mun_code, String zip_code, String landline, String fax, String mobile, String email, String tax_type, String form_type, String atc, String id_type, String id_number, String id_effectivity, String id_expiry, String id_issuer, String id_place) {
        // super(): Sends personal details to the Person class so we can reuse its code.
        super(taxpayer_fullname, gender, civil_status, date_of_birth, place_of_birth);
        setCitizenship(citizenship); // Set citizenship first
        setForeign_address(foreign_address);
        setTaxpayer_tin(taxpayer_tin);
        setBir_reg_date(bir_reg_date);
        setPcn(pcn);
        setTaxpayer_type(taxpayer_type);
        setOther_citizenship(other_citizenship);
        setMother_fullname(mother_fullname);
        setFather_fullname(father_fullname);
        setFull_address(full_address);
        setMun_code(mun_code);
        setZip_code(zip_code);
        setLandline(landline);
        setFax(fax);
        setMobile(mobile);
        setEmail(email);
        setTax_type(tax_type);
        setForm_type(form_type);
        setAtc(atc);
        setId_type(id_type);
        setId_number(id_number);
        setId_effectivity(id_effectivity);
        setId_expiry(id_expiry);
        setId_issuer(id_issuer);
        setId_place(id_place);
    }

    // Getters and Setters
    public int getApplicant_id() {
        return applicant_id;
    }

    public final void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getTaxpayer_tin() {
        return taxpayer_tin;
    }

    public final void setTaxpayer_tin(String taxpayer_tin) {
        if (taxpayer_tin != null && !taxpayer_tin.isEmpty()) {
            if (!taxpayer_tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
                throw new IllegalArgumentException("Taxpayer TIN must be in 000-000-000-000 format.");
            }
            this.taxpayer_tin = taxpayer_tin;
        } else {
            this.taxpayer_tin = null;
        }
    }

    public String getBir_reg_date() {
        return bir_reg_date;
    }

    public final void setBir_reg_date(String bir_reg_date) {
        if (bir_reg_date != null && !bir_reg_date.isEmpty()) {
            if (!bir_reg_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("BIR registration date must be in YYYY-MM-DD format.");
            }
            this.bir_reg_date = bir_reg_date;
        } else {
            this.bir_reg_date = null;
        }
    }

    public String getPcn() {
        return pcn;
    }

    public final void setPcn(String pcn) {
        this.pcn = pcn;
    }

    public String getTaxpayer_type() {
        return taxpayer_type;
    }

    public final void setTaxpayer_type(String taxpayer_type) {
        if (taxpayer_type == null || taxpayer_type.isEmpty()) {
            this.taxpayer_type = "Individual";
        } else {
            this.taxpayer_type = taxpayer_type;
        }
    }

    public String getCitizenship() {
        return citizenship;
    }

    public final void setCitizenship(String citizenship) {
        if (citizenship == null || citizenship.isEmpty()) {
            this.citizenship = "Filipino";
        } else {
            this.citizenship = citizenship;
        }
    }

    public String getOther_citizenship() {
        return other_citizenship;
    }

    public final void setOther_citizenship(String other_citizenship) {
        this.other_citizenship = other_citizenship;
    }

    public String getMother_fullname() {
        return mother_fullname;
    }

    public final void setMother_fullname(String mother_fullname) {
        if (mother_fullname == null || mother_fullname.isEmpty()) {
            throw new IllegalArgumentException("Mother's full name is required.");
        }
        this.mother_fullname = mother_fullname;
    }

    public String getFather_fullname() {
        return father_fullname;
    }

    public final void setFather_fullname(String father_fullname) {
        if (father_fullname == null || father_fullname.isEmpty()) {
            throw new IllegalArgumentException("Father's full name is required.");
        }
        this.father_fullname = father_fullname;
    }

    public String getFull_address() {
        return full_address;
    }

    public final void setFull_address(String full_address) {
        if (full_address == null || full_address.isEmpty()) {
            throw new IllegalArgumentException("Full address is required.");
        }
        this.full_address = full_address;
    }

    public String getForeign_address() {
        return foreign_address;
    }

    public final void setForeign_address(String foreign_address) {
        if (this.citizenship != null && !this.citizenship.equals("Filipino")) {
            if (foreign_address == null || foreign_address.isEmpty()) {
                throw new IllegalArgumentException("Foreign Address is required for non-Filipino citizens.");
            }
        }
        this.foreign_address = foreign_address;
    }

    public String getMun_code() {
        return mun_code;
    }

    public final void setMun_code(String mun_code) {
        if (mun_code == null || mun_code.isEmpty()) {
            throw new IllegalArgumentException("Municipality code is required.");
        }
        this.mun_code = mun_code;
    }

    public String getZip_code() {
        return zip_code;
    }

    public final void setZip_code(String zip_code) {
        if (zip_code == null || zip_code.isEmpty()) {
            throw new IllegalArgumentException("Zip code is required.");
        }
        this.zip_code = zip_code;
    }

    public String getLandline() {
        return landline;
    }

    public final void setLandline(String landline) {
        this.landline = landline;
    }

    public String getFax() {
        return fax;
    }

    public final void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public final void setMobile(String mobile) {
        if (mobile == null || !mobile.matches("09\\d{9}")) {
            throw new IllegalArgumentException("Mobile number must be 11 digits starting with '09' (e.g., 09123456789).");
        }
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getTax_type() {
        return tax_type;
    }

    public final void setTax_type(String tax_type) {
        if (tax_type == null || tax_type.isEmpty()) {
            this.tax_type = "Income";
        } else {
            this.tax_type = tax_type;
        }
    }

    public String getForm_type() {
        return form_type;
    }

    public final void setForm_type(String form_type) {
        if (form_type == null || form_type.isEmpty()) {
            this.form_type = "1902";
        } else {
            this.form_type = form_type;
        }
    }

    public String getAtc() {
        return atc;
    }

    public final void setAtc(String atc) {
        if (atc == null || atc.isEmpty()) {
            this.atc = "II011";
        } else {
            this.atc = atc;
        }
    }

    public String getId_type() {
        return id_type;
    }

    public final void setId_type(String id_type) {
        if (id_type == null || id_type.isEmpty()) {
            throw new IllegalArgumentException("Verification ID Type is required.");
        }
        java.util.List<String> validIds = java.util.Arrays.asList(
            "PhilID", "Passport", "Driver's License", "UMID", "SSS", "PRC", "GSIS", "Postal ID", "Voter's ID"
        );
        if (!validIds.contains(id_type)) {
            throw new IllegalArgumentException("Invalid ID Type. Must be one of: PhilID, Passport, Driver's License, UMID, SSS, PRC, GSIS, Postal ID, Voter's ID.");
        }
        this.id_type = id_type;
    }

    public String getId_number() {
        return id_number;
    }

    public final void setId_number(String id_number) {
        if (id_number == null || id_number.isEmpty()) {
            throw new IllegalArgumentException("Verification ID Number is required.");
        }
        this.id_number = id_number;
    }

    public String getId_effectivity() {
        return id_effectivity;
    }

    public final void setId_effectivity(String id_effectivity) {
        if (id_effectivity == null || !id_effectivity.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("ID Effectivity Date must be in YYYY-MM-DD format.");
        }
        this.id_effectivity = id_effectivity;
    }

    public String getId_expiry() {
        return id_expiry;
    }

    public final void setId_expiry(String id_expiry) {
        if (id_expiry == null || !id_expiry.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("ID Expiry Date must be in YYYY-MM-DD format.");
        }
        this.id_expiry = id_expiry;
    }

    public String getId_issuer() {
        return id_issuer;
    }

    public final void setId_issuer(String id_issuer) {
        if (id_issuer == null || id_issuer.isEmpty()) {
            throw new IllegalArgumentException("ID Issuer is required.");
        }
        this.id_issuer = id_issuer;
    }

    public String getId_place() {
        return id_place;
    }

    public final void setId_place(String id_place) {
        if (id_place == null || id_place.isEmpty()) {
            throw new IllegalArgumentException("ID Place of Issuance is required.");
        }
        this.id_place = id_place;
    }
    
    //Implements the getSummary method in the Person 
    //that returns a string of the important taxpayer details
    @Override
    public String getSummary() {
        return getTaxpayer_fullname() 
        + " | " + getGender() + " | " + getCivil_status() + " | TIN: " + taxpayer_tin + " | Type: " + taxpayer_type;
    }
}