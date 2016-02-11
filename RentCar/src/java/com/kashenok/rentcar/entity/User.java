package com.kashenok.rentcar.entity;

/**
 * class User.
 */
public class User extends Entity {

    private static final long serialVersionUID = -8840511086564085400L;

    private Integer userId;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private Double balance;
    private UserRole role;

    public User() {

    }

    /**
     *
     * @param login
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param passportNumber is unique String of every User.
     */
    public User(String login, String password, String email, String firstName, String lastName, String passportNumber) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", login=" + login + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", passportNumber=" + passportNumber + ", balance=" + balance + ", role=" + role + '}';
    }

    @Override
    public int hashCode() {
        final int a = 17;
        int result = super.hashCode();
        result = result * a + login.hashCode();
        result = result * a + password.hashCode();
        result = result * a + email.hashCode();
        result = result * a + firstName.hashCode();
        result = result * a + lastName.hashCode();
        result = result * a + passportNumber.hashCode();
        result = result * a + role.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (passportNumber == null) {
            if (other.passportNumber != null) {
                return false;
            }
        } else if (!passportNumber.equals(other.passportNumber)) {
            return false;
        }
        if (balance != other.balance) {
            return false;
        }
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }

        return true;
    }

}
