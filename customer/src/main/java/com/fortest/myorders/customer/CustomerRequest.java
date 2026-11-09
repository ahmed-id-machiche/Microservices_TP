package com.fortest.myorders.customer;

public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String email;

    public CustomerRequest() {
    }

    public CustomerRequest(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static CustomerRequestBuilder builder() {
        return new CustomerRequestBuilder();
    }

    public static class CustomerRequestBuilder {
        private String firstName;
        private String lastName;
        private String email;

        public CustomerRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerRequest build() {
            return new CustomerRequest(firstName, lastName, email);
        }
    }
}
