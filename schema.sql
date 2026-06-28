PRAGMA foreign_keys = ON;

CREATE TABLE location (
    mun_code TEXT PRIMARY KEY,
    mun TEXT NOT NULL,
    rdo_code TEXT NOT NULL
);

CREATE TABLE taxpayer (
    applicant_id INTEGER PRIMARY KEY AUTOINCREMENT,
    taxpayer_tin TEXT NULL,
    bir_reg_date TEXT DEFAULT CURRENT_DATE,
    pcn TEXT NULL,
    taxpayer_type TEXT NOT NULL,
    taxpayer_fullname TEXT NOT NULL,
    gender TEXT NOT NULL CHECK (gender IN ('Male', 'Female')),
    civil_status TEXT NOT NULL CHECK (civil_status IN ('Single', 'Married', 'Widow/er', 'Legally Separated', 'With Qualified Dependent Child/ren')),
    date_of_birth TEXT NOT NULL, 
    place_of_birth TEXT NOT NULL,
    citizenship TEXT NOT NULL,
    other_citizenship TEXT NULL,
    mother_fullname TEXT NOT NULL,
    father_fullname TEXT NOT NULL,
    full_address TEXT NOT NULL,
    foreign_address TEXT NULL,
    mun_code TEXT NOT NULL,
    zip_code TEXT NOT NULL,
    landline TEXT NULL,
    fax TEXT NULL,
    mobile TEXT NULL,
    email TEXT NOT NULL,
    tax_type TEXT NOT NULL DEFAULT 'Income Tax',
    form_type TEXT NOT NULL DEFAULT '1700',
    atc TEXT NOT NULL DEFAULT 'II011',
    id_type TEXT NOT NULL,
    id_number TEXT NOT NULL UNIQUE,
    id_effectivity TEXT NOT NULL,
    id_expiry TEXT NOT NULL,
    id_issuer TEXT NOT NULL,
    id_place TEXT NOT NULL,
    FOREIGN KEY (mun_code) REFERENCES location(mun_code)
);

CREATE TABLE dependents (
    dependent_id INTEGER PRIMARY KEY AUTOINCREMENT,
    applicant_id INTEGER NOT NULL,
    dependent_fullname TEXT NOT NULL,
    dependent_dob TEXT NOT NULL,
    is_incapacitated TEXT NOT NULL CHECK (is_incapacitated IN ('Yes', 'No')),
    FOREIGN KEY (applicant_id) REFERENCES taxpayer(applicant_id)
);

CREATE TABLE employer (
    emp_tin TEXT PRIMARY KEY, 
    emp_fullname TEXT NOT NULL,
    emp_full_address TEXT NULL,
    zip_code TEXT NULL,
    emp_landline TEXT NULL,
    emp_mun_code TEXT NULL,
    registering_office_type TEXT NULL,
    FOREIGN KEY (emp_mun_code) REFERENCES location(mun_code)
);

CREATE TABLE spouse (
    applicant_id INTEGER PRIMARY KEY,
    spouse_fullname TEXT NOT NULL,
    spouse_employment_status TEXT NOT NULL CHECK (spouse_employment_status IN ('Unemployed', 'Employed Locally', 'Employed Abroad', 'Engaged in Business/Practice of Profession')),
    exemption_claimant TEXT NULL CHECK (exemption_claimant IN ('Husband Claims', 'Wife Claims')),
    spouse_emp_tin TEXT NULL,
    spouse_tin TEXT NULL, 
    FOREIGN KEY (applicant_id) REFERENCES taxpayer(applicant_id),
    FOREIGN KEY (spouse_emp_tin) REFERENCES employer(emp_tin)
);

CREATE TABLE employee_relationship (
    applicant_id INTEGER, 
    emp_tin TEXT, 
    emp_type TEXT NOT NULL CHECK (emp_type IN ('Primary', 'Concurrent', 'Successive')),
    hire_date TEXT NOT NULL,
    PRIMARY KEY (applicant_id, emp_tin),
    FOREIGN KEY (applicant_id) REFERENCES taxpayer(applicant_id),
    FOREIGN KEY (emp_tin) REFERENCES employer(emp_tin)
);

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);
