# TIN Registry: An Automated Employee Tax Registration System

A Java Swing desktop application that automates BIR Form 1902 (Employee's Registration for Withholding Tax). Built with SQLite and an MVC-like architecture using OOP principles.

## How It Works

The system models the full BIR employee tax registration workflow through five core entities:

- **Taxpayer** (extends `Person`) — personal info, TIN, citizenship, contact details, and government ID verification
- **Spouse** — linked to the taxpayer if civil status is Married
- **Dependent** — up to 4 qualified dependent children per taxpayer
- **Employer** — primary employer details plus optional successive/concurrent employer
- **Employee_Relationship** — links taxpayer to employer(s) with employment type and hire date
- **Location** — municipality/RDO code lookup for auto-filling fields

### Architecture

```
src/
  view/       — Java Swing GUI panels (Login, MainMenu, CRUD screens)
  model/      — Data classes (Person, Taxpayer, Spouse, Dependent, Employer, etc.)
  dao/        — Sql.java handles all SQLite database operations via JDBC
```

The app uses `CardLayout` to switch between feature panels (Add, View, Search, Update, Delete, Report) and `JTabbedPane` within Add/Update to organize Taxpayer, Spouse, Dependent, and Employer sections.

## Tutorial

### Running the Application

1. Ensure Java 8+ is installed
2. Run `run.bat` (compiles all `.java` files with the SQLite JDBC driver and launches the app)
3. The app uses `OOPgroup2finaldb.db` as the database (auto-created via `schema.sql` if missing)

### Login / Sign Up

- On launch, a **Login** screen appears. Enter your username and password.
- New users can click **Sign Up** to create an account, then are automatically redirected to Login.
- After 3 failed login attempts, the application exits.

### Main Menu Navigation

After logging in, the header bar shows these buttons:

| Button | Function |
|--------|----------|
| Add Application | Register a new taxpayer |
| View Application | List all registered taxpayers |
| Search Application | Find taxpayers by name, TIN, or ID |
| Update Application | Modify an existing record |
| Delete Application | Permanently remove a record |
| Report | View total and pending application counts |

### Adding an Application

1. Click **Add Application** in the header
2. Fill in the **Taxpayer** tab (required fields: full name, place of birth, mother/father name, citizenship, email, address, municipality, zip code, ID details)
3. If civil status is **Married**, fill the **Spouse** tab
4. Optionally add up to **4 Dependents** with their date of birth and incapacitated status
5. Fill the **Employer** tab (at minimum, employer TIN and name)
6. The **Submit** button activates once all required fields are complete
7. Click **Submit** — the system validates all inputs and saves the record, then displays the assigned Applicant ID

### Viewing Applications

- Click **View Application** to see a table of all registered taxpayers
- **Double-click** any row to see the full record details
- Click **Refresh** to reload data

### Searching Applications

- Click **Search Application**, enter a name, TIN, or applicant ID
- Results appear in a table
- **Double-click** a result or if only one match is found, the full record details auto-display

### Updating an Application

1. Click **Update Application**, enter the **Applicant ID**, and click **Search**
2. The existing data loads into the tabs — modify any field
3. Click **Update Application** to save changes

### Deleting an Application

1. Click **Delete Application**, enter the **Applicant ID**, and click **Search**
2. Review the displayed record
3. Click **Delete Record** and confirm — this permanently removes the taxpayer, spouse, dependents, and employer relationships

### Reports

The **Report** panel shows:
- **Total Applications** — count of all registered taxpayers
- **Pending Applications** — count of taxpayers with incomplete data (no TIN assigned)

Click **Refresh Report** to update counts.

---

*This project is for our Java Object-Oriented Programming Subject/Course Final Project at the Polytechnic University of the Philippines.*
