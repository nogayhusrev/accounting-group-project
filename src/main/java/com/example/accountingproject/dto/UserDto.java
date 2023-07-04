package com.example.accountingproject.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class UserDto {
        private Long id;

        @NotBlank
        @Email
        private String username;

        @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
        private String password;//strong password

        @NotNull
        private String confirmPassword;

        @NotNull
        private CompanyDto company;

        @NotBlank
        @Size(min = 2, max = 50)
        private String firstname;

        @NotBlank
        @Size(min = 2, max = 50)
        private String lastname;

        @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")                     // +111 123 45 67 89
        private String phone; //Can be customized according to country

        @NotNull
        private RoleDto role;


        private Boolean isOnlyAdmin;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
            checkConfirmPassword();
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            checkConfirmPassword();
        }

        private void checkConfirmPassword() {
            if (password != null && !password.equals(confirmPassword)) {
                this.confirmPassword = null;
            }
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public RoleDto getRole() {
            return role;
        }

        public void setRole(RoleDto role) {
            this.role = role;
        }

        public CompanyDto getCompany() {
            return company;
        }

        public void setCompany(CompanyDto company) {
            this.company = company;
        }

        public Boolean getIsOnlyAdmin() {
            return isOnlyAdmin;
        }

        public void setIsOnlyAdmin(Boolean isOnlyAdmin) {
            this.isOnlyAdmin = isOnlyAdmin;
        }
    }
