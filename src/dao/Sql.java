package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Dependent;
import model.Employee_Relationship;
import model.Employer;
import model.Location;
import model.Spouse;
import model.Taxpayer;

public class Sql {
    // Database connection URL
    private static final String DATABASE_URL = "jdbc:sqlite:OOPgroup2finaldb.db";

    // Constructor to initialize users table and default admin
    public Sql() {
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement()) {
            
            // Create users table if not exists
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                              "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              "username TEXT NOT NULL UNIQUE, " +
                              "password TEXT NOT NULL" +
                              ");");
            
            // Insert default admin if users table is empty
            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users;")) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    statement.execute("INSERT INTO users (username, password) VALUES ('admin', 'admin123');");
                    System.out.println("Default admin user created: admin/admin123");
                }
            }
        } catch (Exception error) {
            System.out.println("Database initialization error: " + error.getMessage());
        }
    }

    // Connect to the SQLite database
    public Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(DATABASE_URL);
    }

    // Check if the login username and password are correct
    public boolean validateLogin(String username, String password) {
        String sqlQuery = "SELECT * FROM users WHERE username = ? AND password = ?;";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception error) {
            System.out.println("Login error: " + error.getMessage());
            return false;
        }
    }

    // Register a new user
    public boolean registerUser(String username, String password) {
        if (checkUserExists(username)) {
            return false;
        }
        String sqlQuery = "INSERT INTO users (username, password) VALUES (?, ?);";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception error) {
            System.out.println("Registration error: " + error.getMessage());
            return false;
        }
    }

    // Check if username is already taken
    public boolean checkUserExists(String username) {
        String sqlQuery = "SELECT * FROM users WHERE username = ?;";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception error) {
            System.out.println("Error checking user: " + error.getMessage());
            return false;
        }
    }

    // Check if a taxpayer is already registered in the database by their full name or TIN
    public boolean checkTaxpayerExists(String fullName, String tin) {
        String sqlQuery;
        if (tin != null && !tin.trim().isEmpty()) {
            sqlQuery = "SELECT * FROM taxpayer WHERE taxpayer_fullname = ? OR taxpayer_tin = ?;";
        } else {
            sqlQuery = "SELECT * FROM taxpayer WHERE taxpayer_fullname = ?;";
        }

        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, fullName);
            if (tin != null && !tin.trim().isEmpty()) {
                preparedStatement.setString(2, tin);
            }
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception error) {
            System.out.println("Error checking: " + error.getMessage());
            return false;
        }
    }

    // Find all towns/municipalities from the database
    public List<Location> getLocations() {
        List<Location> locationList = new ArrayList<>();
        String sqlQuery = "SELECT mun_code, mun, rdo_code FROM location ORDER BY mun ASC;";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            
            while (resultSet.next()) {
                Location location = new Location(
                    resultSet.getString("mun_code"),
                    resultSet.getString("mun"),
                    resultSet.getString("rdo_code")
                );
                locationList.add(location);
            }
        } catch (Exception error) {
            System.out.println("Failed to find locations: " + error.getMessage());
        }
        return locationList;
    }

    // Save a new taxpayer profile and return their generated ID number
    public int saveTaxpayer(Taxpayer taxpayer) {
        String sqlQuery = "INSERT INTO taxpayer (taxpayer_tin, bir_reg_date, pcn, taxpayer_type, taxpayer_fullname, gender, civil_status, date_of_birth, place_of_birth, citizenship, other_citizenship, mother_fullname, father_fullname, full_address, foreign_address, mun_code, zip_code, landline, fax, mobile, email, tax_type, form_type, atc, id_type, id_number, id_effectivity, id_expiry, id_issuer, id_place) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int generatedId = -1;

        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, taxpayer.getTaxpayer_tin());
            preparedStatement.setString(2, taxpayer.getBir_reg_date());
            preparedStatement.setString(3, taxpayer.getPcn());
            preparedStatement.setString(4, taxpayer.getTaxpayer_type());
            preparedStatement.setString(5, taxpayer.getTaxpayer_fullname());
            preparedStatement.setString(6, taxpayer.getGender());
            preparedStatement.setString(7, taxpayer.getCivil_status());
            preparedStatement.setString(8, taxpayer.getDate_of_birth());
            preparedStatement.setString(9, taxpayer.getPlace_of_birth());
            preparedStatement.setString(10, taxpayer.getCitizenship());
            preparedStatement.setString(11, taxpayer.getOther_citizenship());
            preparedStatement.setString(12, taxpayer.getMother_fullname());
            preparedStatement.setString(13, taxpayer.getFather_fullname());
            preparedStatement.setString(14, taxpayer.getFull_address());
            preparedStatement.setString(15, taxpayer.getForeign_address());
            preparedStatement.setString(16, taxpayer.getMun_code());
            preparedStatement.setString(17, taxpayer.getZip_code());
            preparedStatement.setString(18, taxpayer.getLandline());
            preparedStatement.setString(19, taxpayer.getFax());
            preparedStatement.setString(20, taxpayer.getMobile());
            preparedStatement.setString(21, taxpayer.getEmail());
            preparedStatement.setString(22, taxpayer.getTax_type());
            preparedStatement.setString(23, taxpayer.getForm_type());
            preparedStatement.setString(24, taxpayer.getAtc());
            preparedStatement.setString(25, taxpayer.getId_type());
            preparedStatement.setString(26, taxpayer.getId_number());
            preparedStatement.setString(27, taxpayer.getId_effectivity());
            preparedStatement.setString(28, taxpayer.getId_expiry());
            preparedStatement.setString(29, taxpayer.getId_issuer());
            preparedStatement.setString(30, taxpayer.getId_place());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (Exception error) {
            System.out.println("Error saving Taxpayer: " + error.getMessage());
            throw new RuntimeException(error.getMessage());
        }
        return generatedId;
    }

    // Save employer details (skips if they are already in the database)
    public void saveEmployerIfNotExists(Employer employer) {
        String sqlQuery = "INSERT OR IGNORE INTO employer (emp_tin, emp_fullname, emp_full_address, zip_code, emp_landline, emp_mun_code, registering_office_type) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, employer.getEmp_tin());
            preparedStatement.setString(2, employer.getEmp_fullname());
            preparedStatement.setString(3, employer.getEmp_full_address());
            preparedStatement.setString(4, employer.getZip_code());
            preparedStatement.setString(5, employer.getEmp_landline());
            preparedStatement.setString(6, employer.getEmp_mun_code());
            preparedStatement.setString(7, employer.getRegistering_office_type());
            
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            System.out.println("Error saving Employer: " + error.getMessage());
        }
    }

    // Save spouse details linked to the taxpayer ID
    public void saveSpouse(Spouse spouse) {
        String sqlQuery = "INSERT INTO spouse (applicant_id, spouse_fullname, spouse_employment_status, exemption_claimant, spouse_emp_tin, spouse_tin) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setInt(1, spouse.getApplicant_id());
            preparedStatement.setString(2, spouse.getSpouse_fullname());
            preparedStatement.setString(3, spouse.getSpouse_employment_status());
            preparedStatement.setString(4, spouse.getExemption_claimant());
            
            if (spouse.getSpouse_emp_tin() != null) {
                preparedStatement.setString(5, spouse.getSpouse_emp_tin());
            } else {
                preparedStatement.setNull(5, java.sql.Types.VARCHAR);
            }
            
            preparedStatement.setString(6, spouse.getSpouse_tin());
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            System.out.println("Error saving Spouse: " + error.getMessage());
        }
    }

    // Save a dependent linked to the taxpayer ID
    public void saveDependent(Dependent dependent) {
        String sqlQuery = "INSERT INTO dependents (applicant_id, dependent_fullname, dependent_dob, is_incapacitated) VALUES (?, ?, ?, ?);";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setInt(1, dependent.getApplicant_id());
            preparedStatement.setString(2, dependent.getDependent_fullname());
            preparedStatement.setString(3, dependent.getDependent_dob());
            preparedStatement.setString(4, dependent.getIs_incapacitated());
            
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            System.out.println("Error saving Dependent: " + error.getMessage());
        }
    }

    // Link a taxpayer to an employer (job record)
    public void saveEmployeeRelationship(Employee_Relationship relationship) {
        String checkSql = "SELECT bir_reg_date FROM taxpayer WHERE applicant_id = ?;";
        String taxpayerRegDate = null;
        try (Connection connection = this.connect();
             PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, relationship.getApplicant_id());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    taxpayerRegDate = rs.getString("bir_reg_date");
                }
            }
        } catch (Exception error) {
            System.out.println("Failed to fetch taxpayer registration date: " + error.getMessage());
        }

        if (taxpayerRegDate != null && relationship.getHire_date() != null) {
            try {
                java.time.LocalDate regDate = java.time.LocalDate.parse(taxpayerRegDate);
                java.time.LocalDate hireDate = java.time.LocalDate.parse(relationship.getHire_date());
                long days = java.time.temporal.ChronoUnit.DAYS.between(hireDate, regDate);
                if (days > 10) {
                    throw new IllegalArgumentException("Filing deadline violated: Must be registered within 10 days from the hire date. Elapsed: " + days + " days.");
                }
            } catch (java.time.format.DateTimeParseException e) {
                // Ignore parsing errors, or handle
            }
        }

        String sqlQuery = "INSERT INTO employee_relationship (applicant_id, emp_tin, emp_type, hire_date) VALUES (?, ?, ?, ?);";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setInt(1, relationship.getApplicant_id());
            preparedStatement.setString(2, relationship.getEmp_tin());
            preparedStatement.setString(3, relationship.getEmp_type());
            preparedStatement.setString(4, relationship.getHire_date());
            
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            System.out.println("Error saving Employee Relationship: " + error.getMessage());
        }
    }

    // Get Taxpayer by ID
    public Taxpayer getTaxpayerById(int id) {
        String sqlQuery = "SELECT * FROM taxpayer WHERE applicant_id = ?;";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                return new Taxpayer(
                    rs.getInt("applicant_id"),
                    rs.getString("taxpayer_tin"),
                    rs.getString("bir_reg_date"),
                    rs.getString("pcn"),
                    rs.getString("taxpayer_type"),
                    rs.getString("taxpayer_fullname"),
                    rs.getString("gender"),
                    rs.getString("civil_status"),
                    rs.getString("date_of_birth"),
                    rs.getString("place_of_birth"),
                    rs.getString("citizenship"),
                    rs.getString("other_citizenship"),
                    rs.getString("mother_fullname"),
                    rs.getString("father_fullname"),
                    rs.getString("full_address"),
                    rs.getString("foreign_address"),
                    rs.getString("mun_code"),
                    rs.getString("zip_code"),
                    rs.getString("landline"),
                    rs.getString("fax"),
                    rs.getString("mobile"),
                    rs.getString("email"),
                    rs.getString("tax_type"),
                    rs.getString("form_type"),
                    rs.getString("atc"),
                    rs.getString("id_type"),
                    rs.getString("id_number"),
                    rs.getString("id_effectivity"),
                    rs.getString("id_expiry"),
                    rs.getString("id_issuer"),
                    rs.getString("id_place")
                );
            }
        } catch (Exception error) {
            System.out.println("Error fetching taxpayer by ID: " + error.getMessage());
        }
        return null;
    }

    // Update Taxpayer
    public boolean updateTaxpayer(Taxpayer taxpayer) {
        String sqlQuery = "UPDATE taxpayer SET taxpayer_tin=?, bir_reg_date=?, pcn=?, taxpayer_type=?, taxpayer_fullname=?, gender=?, civil_status=?, date_of_birth=?, place_of_birth=?, citizenship=?, other_citizenship=?, mother_fullname=?, father_fullname=?, full_address=?, foreign_address=?, mun_code=?, zip_code=?, landline=?, fax=?, mobile=?, email=?, tax_type=?, form_type=?, atc=?, id_type=?, id_number=?, id_effectivity=?, id_expiry=?, id_issuer=?, id_place=? WHERE applicant_id=?;";
        
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            
            preparedStatement.setString(1, taxpayer.getTaxpayer_tin());
            preparedStatement.setString(2, taxpayer.getBir_reg_date());
            preparedStatement.setString(3, taxpayer.getPcn());
            preparedStatement.setString(4, taxpayer.getTaxpayer_type());
            preparedStatement.setString(5, taxpayer.getTaxpayer_fullname());
            preparedStatement.setString(6, taxpayer.getGender());
            preparedStatement.setString(7, taxpayer.getCivil_status());
            preparedStatement.setString(8, taxpayer.getDate_of_birth());
            preparedStatement.setString(9, taxpayer.getPlace_of_birth());
            preparedStatement.setString(10, taxpayer.getCitizenship());
            preparedStatement.setString(11, taxpayer.getOther_citizenship());
            preparedStatement.setString(12, taxpayer.getMother_fullname());
            preparedStatement.setString(13, taxpayer.getFather_fullname());
            preparedStatement.setString(14, taxpayer.getFull_address());
            preparedStatement.setString(15, taxpayer.getForeign_address());
            preparedStatement.setString(16, taxpayer.getMun_code());
            preparedStatement.setString(17, taxpayer.getZip_code());
            preparedStatement.setString(18, taxpayer.getLandline());
            preparedStatement.setString(19, taxpayer.getFax());
            preparedStatement.setString(20, taxpayer.getMobile());
            preparedStatement.setString(21, taxpayer.getEmail());
            preparedStatement.setString(22, taxpayer.getTax_type());
            preparedStatement.setString(23, taxpayer.getForm_type());
            preparedStatement.setString(24, taxpayer.getAtc());
            preparedStatement.setString(25, taxpayer.getId_type());
            preparedStatement.setString(26, taxpayer.getId_number());
            preparedStatement.setString(27, taxpayer.getId_effectivity());
            preparedStatement.setString(28, taxpayer.getId_expiry());
            preparedStatement.setString(29, taxpayer.getId_issuer());
            preparedStatement.setString(30, taxpayer.getId_place());
            preparedStatement.setInt(31, taxpayer.getApplicant_id());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception error) {
            System.out.println("Error updating Taxpayer: " + error.getMessage());
            return false;
        }
    }

    // Get a list of all registered taxpayers
    public List<Taxpayer> getAllTaxpayers() {
        List<Taxpayer> taxpayerList = new ArrayList<>();
        String sqlQuery = "SELECT applicant_id, taxpayer_fullname, taxpayer_tin, email, mobile, civil_status FROM taxpayer ORDER BY applicant_id DESC;";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            
            while (resultSet.next()) {
                Taxpayer taxpayer = new Taxpayer();
                taxpayer.setApplicant_id(resultSet.getInt("applicant_id"));
                taxpayer.setTaxpayer_fullname(resultSet.getString("taxpayer_fullname"));
                taxpayer.setTaxpayer_tin(resultSet.getString("taxpayer_tin"));
                taxpayer.setEmail(resultSet.getString("email"));
                taxpayer.setMobile(resultSet.getString("mobile"));
                taxpayer.setCivil_status(resultSet.getString("civil_status"));
                taxpayerList.add(taxpayer);
            }
        } catch (Exception error) {
            System.out.println("Failed to find taxpayers: " + error.getMessage());
        }
        return taxpayerList;
    }

    // Get a single taxpayer's full details as a formatted string for display
    public String getTaxpayerDetailsById(int id) {
        StringBuilder details = new StringBuilder();
        String sqlQuery = "SELECT * FROM taxpayer WHERE applicant_id = ?;";
        try (Connection connection = this.connect();
             PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                details.append("═══════════════════════════════════════════\n");
                details.append("  TAXPAYER INFORMATION\n");
                details.append("═══════════════════════════════════════════\n");
                details.append("  Applicant ID:       ").append(rs.getInt("applicant_id")).append("\n");
                details.append("  Full Name:          ").append(rs.getString("taxpayer_fullname")).append("\n");
                details.append("  TIN:                ").append(nvl(rs.getString("taxpayer_tin"))).append("\n");
                details.append("  Taxpayer Type:      ").append(rs.getString("taxpayer_type")).append("\n");
                details.append("  Gender:             ").append(rs.getString("gender")).append("\n");
                details.append("  Civil Status:       ").append(rs.getString("civil_status")).append("\n");
                details.append("  Date of Birth:      ").append(rs.getString("date_of_birth")).append("\n");
                details.append("  Place of Birth:     ").append(rs.getString("place_of_birth")).append("\n");
                details.append("  Citizenship:        ").append(rs.getString("citizenship")).append("\n");
                details.append("  Other Citizenship:  ").append(nvl(rs.getString("other_citizenship"))).append("\n");
                details.append("  Mother's Name:      ").append(rs.getString("mother_fullname")).append("\n");
                details.append("  Father's Name:      ").append(rs.getString("father_fullname")).append("\n");
                details.append("  Address:            ").append(rs.getString("full_address")).append("\n");
                details.append("  Foreign Address:    ").append(nvl(rs.getString("foreign_address"))).append("\n");
                details.append("  Municipality Code:  ").append(rs.getString("mun_code")).append("\n");
                details.append("  ZIP Code:           ").append(rs.getString("zip_code")).append("\n");
                details.append("  Landline:           ").append(nvl(rs.getString("landline"))).append("\n");
                details.append("  Fax:                ").append(nvl(rs.getString("fax"))).append("\n");
                details.append("  Mobile:             ").append(nvl(rs.getString("mobile"))).append("\n");
                details.append("  Email:              ").append(rs.getString("email")).append("\n");
                details.append("  Tax Type:           ").append(rs.getString("tax_type")).append("\n");
                details.append("  Form Type:          ").append(rs.getString("form_type")).append("\n");
                details.append("  ATC:                ").append(rs.getString("atc")).append("\n");
                details.append("  ID Type:            ").append(rs.getString("id_type")).append("\n");
                details.append("  ID Number:          ").append(rs.getString("id_number")).append("\n");
                details.append("  ID Effectivity:     ").append(rs.getString("id_effectivity")).append("\n");
                details.append("  ID Expiry:          ").append(rs.getString("id_expiry")).append("\n");
                details.append("  ID Issuer:          ").append(rs.getString("id_issuer")).append("\n");
                details.append("  ID Place:           ").append(rs.getString("id_place")).append("\n");

                // Fetch spouse info
                String spouseSql = "SELECT * FROM spouse WHERE applicant_id = ?;";
                try (PreparedStatement spousePs = connection.prepareStatement(spouseSql)) {
                    spousePs.setInt(1, id);
                    ResultSet spouseRs = spousePs.executeQuery();
                    if (spouseRs.next()) {
                        details.append("\n═══════════════════════════════════════════\n");
                        details.append("  SPOUSE INFORMATION\n");
                        details.append("═══════════════════════════════════════════\n");
                        details.append("  Spouse Name:        ").append(spouseRs.getString("spouse_fullname")).append("\n");
                        details.append("  Employment Status:  ").append(spouseRs.getString("spouse_employment_status")).append("\n");
                        details.append("  Exemption Claimant: ").append(nvl(spouseRs.getString("exemption_claimant"))).append("\n");
                        details.append("  Spouse TIN:         ").append(nvl(spouseRs.getString("spouse_tin"))).append("\n");
                        details.append("  Spouse Emp TIN:     ").append(nvl(spouseRs.getString("spouse_emp_tin"))).append("\n");
                    }
                }

                // Fetch dependents
                String depSql = "SELECT * FROM dependents WHERE applicant_id = ?;";
                try (PreparedStatement depPs = connection.prepareStatement(depSql)) {
                    depPs.setInt(1, id);
                    ResultSet depRs = depPs.executeQuery();
                    boolean hasDepHeader = false;
                    int depCount = 1;
                    while (depRs.next()) {
                        if (!hasDepHeader) {
                            details.append("\n═══════════════════════════════════════════\n");
                            details.append("  DEPENDENTS\n");
                            details.append("═══════════════════════════════════════════\n");
                            hasDepHeader = true;
                        }
                        details.append("  Dependent #").append(depCount++).append(":\n");
                        details.append("    Name:             ").append(depRs.getString("dependent_fullname")).append("\n");
                        details.append("    Date of Birth:    ").append(depRs.getString("dependent_dob")).append("\n");
                        details.append("    Incapacitated:    ").append(depRs.getString("is_incapacitated")).append("\n");
                    }
                }

                // Fetch employer relationships
                String empSql = "SELECT er.emp_tin, er.emp_type, er.hire_date, e.emp_fullname, e.emp_full_address " +
                                "FROM employee_relationship er " +
                                "LEFT JOIN employer e ON er.emp_tin = e.emp_tin " +
                                "WHERE er.applicant_id = ?;";
                try (PreparedStatement empPs = connection.prepareStatement(empSql)) {
                    empPs.setInt(1, id);
                    ResultSet empRs = empPs.executeQuery();
                    boolean hasEmpHeader = false;
                    int empCount = 1;
                    while (empRs.next()) {
                        if (!hasEmpHeader) {
                            details.append("\n═══════════════════════════════════════════\n");
                            details.append("  EMPLOYER INFORMATION\n");
                            details.append("═══════════════════════════════════════════\n");
                            hasEmpHeader = true;
                        }
                        details.append("  Employer #").append(empCount++).append(":\n");
                        details.append("    Name:             ").append(nvl(empRs.getString("emp_fullname"))).append("\n");
                        details.append("    TIN:              ").append(empRs.getString("emp_tin")).append("\n");
                        details.append("    Type:             ").append(empRs.getString("emp_type")).append("\n");
                        details.append("    Hire Date:        ").append(empRs.getString("hire_date")).append("\n");
                        details.append("    Address:          ").append(nvl(empRs.getString("emp_full_address"))).append("\n");
                    }
                }
            }
        } catch (Exception error) {
            System.out.println("Error fetching taxpayer details: " + error.getMessage());
        }
        return details.length() > 0 ? details.toString() : null;
    }

    // Search taxpayers by name or TIN (returns matching list for search)
    public List<Taxpayer> searchTaxpayers(String keyword) {
        List<Taxpayer> results = new ArrayList<>();
        String sqlQuery = "SELECT applicant_id, taxpayer_fullname, taxpayer_tin, email, mobile, civil_status " +
                          "FROM taxpayer WHERE taxpayer_fullname LIKE ? OR taxpayer_tin LIKE ? OR CAST(applicant_id AS TEXT) = ? " +
                          "ORDER BY applicant_id DESC;";
        try (Connection connection = this.connect();
             PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Taxpayer t = new Taxpayer();
                t.setApplicant_id(rs.getInt("applicant_id"));
                t.setTaxpayer_fullname(rs.getString("taxpayer_fullname"));
                t.setTaxpayer_tin(rs.getString("taxpayer_tin"));
                t.setEmail(rs.getString("email"));
                t.setMobile(rs.getString("mobile"));
                t.setCivil_status(rs.getString("civil_status"));
                results.add(t);
            }
        } catch (Exception error) {
            System.out.println("Search error: " + error.getMessage());
        }
        return results;
    }

    // Delete a taxpayer and all related records (cascade)
    public boolean deleteTaxpayerById(int id) {
        String[] deleteStatements = {
            "DELETE FROM dependents WHERE applicant_id = ?;",
            "DELETE FROM employee_relationship WHERE applicant_id = ?;",
            "DELETE FROM spouse WHERE applicant_id = ?;",
            "DELETE FROM taxpayer WHERE applicant_id = ?;"
        };
        try (Connection connection = this.connect()) {
            connection.setAutoCommit(false);
            for (String sql : deleteStatements) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
            connection.commit();
            return true;
        } catch (Exception error) {
            System.out.println("Error deleting taxpayer: " + error.getMessage());
            return false;
        }
    }

    // Get total number of applications for the report panel
    public int getTotalApplicationCount() {
        String sqlQuery = "SELECT COUNT(*) FROM taxpayer;";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sqlQuery)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception error) {
            System.out.println("Error counting applications: " + error.getMessage());
        }
        return 0;
    }

    // Get number of pending applications (no TIN assigned yet)
    public int getPendingApplicationCount() {
        String sqlQuery = "SELECT COUNT(*) FROM taxpayer WHERE taxpayer_tin IS NULL OR taxpayer_tin = '';";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sqlQuery)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception error) {
            System.out.println("Error counting pending: " + error.getMessage());
        }
        return 0;
    }

    // Helper to display "N/A" for null values
    private String nvl(String value) {
        return (value == null || value.isEmpty()) ? "N/A" : value;
    }

    // --- FETCH METHODS FOR UPDATE ---
    
    public Spouse getSpouseByApplicantId(int applicantId) {
        String sql = "SELECT * FROM spouse WHERE applicant_id = ?";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Spouse s = new Spouse();
                s.setApplicant_id(rs.getInt("applicant_id"));
                s.setSpouse_fullname(rs.getString("spouse_fullname"));
                s.setSpouse_employment_status(rs.getString("spouse_employment_status"));
                s.setExemption_claimant(rs.getString("exemption_claimant"));
                s.setSpouse_emp_tin(rs.getString("spouse_emp_tin"));
                s.setSpouse_tin(rs.getString("spouse_tin"));
                return s;
            }
        } catch (Exception e) {
            System.out.println("Error fetching spouse: " + e.getMessage());
        }
        return null;
    }

    public Employee_Relationship getEmployeeRelationshipByApplicantId(int applicantId) {
        String sql = "SELECT * FROM employee_relationship WHERE applicant_id = ?";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee_Relationship er = new Employee_Relationship();
                er.setApplicant_id(rs.getInt("applicant_id"));
                er.setEmp_tin(rs.getString("emp_tin"));
                er.setEmp_type(rs.getString("emp_type"));
                er.setHire_date(rs.getString("hire_date"));
                return er;
            }
        } catch (Exception e) {
            System.out.println("Error fetching employee relationship: " + e.getMessage());
        }
        return null;
    }

    public Employer getEmployerByTin(String tin) {
        String sql = "SELECT * FROM employer WHERE emp_tin = ?";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employer e = new Employer();
                e.setEmp_tin(rs.getString("emp_tin"));
                e.setEmp_fullname(rs.getString("emp_fullname"));
                e.setEmp_full_address(rs.getString("emp_full_address"));
                e.setZip_code(rs.getString("zip_code"));
                e.setEmp_landline(rs.getString("emp_landline"));
                e.setEmp_mun_code(rs.getString("emp_mun_code"));
                e.setRegistering_office_type(rs.getString("registering_office_type"));
                return e;
            }
        } catch (Exception e) {
            System.out.println("Error fetching employer: " + e.getMessage());
        }
        return null;
    }

    public List<Dependent> getDependentsByApplicantId(int applicantId) {
        List<Dependent> list = new ArrayList<>();
        String sql = "SELECT * FROM dependents WHERE applicant_id = ?";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, applicantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dependent d = new Dependent();
                d.setApplicant_id(rs.getInt("applicant_id"));
                d.setDependent_fullname(rs.getString("dependent_fullname"));
                d.setDependent_dob(rs.getString("dependent_dob"));
                d.setIs_incapacitated(rs.getString("is_incapacitated"));
                list.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error fetching dependents: " + e.getMessage());
        }
        return list;
    }

    // --- UPSERT METHODS FOR UPDATE ---
    
    public void upsertSpouse(Spouse spouse) {
        String del = "DELETE FROM spouse WHERE applicant_id = ?";
        String ins = "INSERT INTO spouse (applicant_id, spouse_fullname, spouse_employment_status, exemption_claimant, spouse_emp_tin, spouse_tin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect(); 
             PreparedStatement psDel = conn.prepareStatement(del);
             PreparedStatement psIns = conn.prepareStatement(ins)) {
            
            psDel.setInt(1, spouse.getApplicant_id());
            psDel.executeUpdate();
            
            psIns.setInt(1, spouse.getApplicant_id());
            psIns.setString(2, spouse.getSpouse_fullname());
            psIns.setString(3, spouse.getSpouse_employment_status());
            psIns.setString(4, spouse.getExemption_claimant());
            if (spouse.getSpouse_emp_tin() != null) psIns.setString(5, spouse.getSpouse_emp_tin());
            else psIns.setNull(5, java.sql.Types.VARCHAR);
            psIns.setString(6, spouse.getSpouse_tin());
            psIns.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error upserting spouse: " + e.getMessage());
        }
    }

    public void upsertEmployeeRelationship(Employee_Relationship er) {
        String del = "DELETE FROM employee_relationship WHERE applicant_id = ?";
        String ins = "INSERT INTO employee_relationship (applicant_id, emp_tin, emp_type, hire_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = this.connect(); 
             PreparedStatement psDel = conn.prepareStatement(del);
             PreparedStatement psIns = conn.prepareStatement(ins)) {
            
            psDel.setInt(1, er.getApplicant_id());
            psDel.executeUpdate();
            
            psIns.setInt(1, er.getApplicant_id());
            psIns.setString(2, er.getEmp_tin());
            psIns.setString(3, er.getEmp_type());
            psIns.setString(4, er.getHire_date());
            psIns.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error upserting employee relationship: " + e.getMessage());
        }
    }

    public void upsertEmployer(Employer employer) {
        String del = "DELETE FROM employer WHERE emp_tin = ?";
        String ins = "INSERT INTO employer (emp_tin, emp_fullname, emp_full_address, zip_code, emp_landline, emp_mun_code, registering_office_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect(); 
             PreparedStatement psDel = conn.prepareStatement(del);
             PreparedStatement psIns = conn.prepareStatement(ins)) {
            
            psDel.setString(1, employer.getEmp_tin());
            psDel.executeUpdate();
            
            psIns.setString(1, employer.getEmp_tin());
            psIns.setString(2, employer.getEmp_fullname());
            psIns.setString(3, employer.getEmp_full_address());
            psIns.setString(4, employer.getZip_code());
            psIns.setString(5, employer.getEmp_landline());
            psIns.setString(6, employer.getEmp_mun_code());
            psIns.setString(7, employer.getRegistering_office_type());
            psIns.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error upserting employer: " + e.getMessage());
        }
    }

    public void upsertDependents(int applicantId, List<Dependent> dependents) {
        String del = "DELETE FROM dependents WHERE applicant_id = ?";
        String ins = "INSERT INTO dependents (applicant_id, dependent_fullname, dependent_dob, is_incapacitated) VALUES (?, ?, ?, ?)";
        try (Connection conn = this.connect(); 
             PreparedStatement psDel = conn.prepareStatement(del);
             PreparedStatement psIns = conn.prepareStatement(ins)) {
            
            psDel.setInt(1, applicantId);
            psDel.executeUpdate();
            
            for (Dependent d : dependents) {
                psIns.setInt(1, applicantId);
                psIns.setString(2, d.getDependent_fullname());
                psIns.setString(3, d.getDependent_dob());
                psIns.setString(4, d.getIs_incapacitated());
                psIns.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error upserting dependents: " + e.getMessage());
        }
    }

}
