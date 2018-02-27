package bg.jug.microprofile.hol.authors;

import java.io.Serializable;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
public class Author implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isRegular;
    private int salary;

    public Author() {
        // empty constructor
    }

    public Author(String firstName, String lastName, String email, boolean isRegular, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isRegular = isRegular;
        this.salary = salary;
    }

    public Author(Long id, String firstName, String lastName, String email, boolean isRegular, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isRegular = isRegular;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isRegular() {
        return isRegular;
    }

    public void setRegular(boolean regular) {
        isRegular = regular;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (isRegular != author.isRegular) return false;
        if (salary != author.salary) return false;
        if (!firstName.equals(author.firstName)) return false;
        if (!lastName.equals(author.lastName)) return false;
        return email.equals(author.email);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (isRegular ? 1 : 0);
        result = 31 * result + salary;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isRegular=" + isRegular +
                ", salary=" + salary +
                '}';
    }
}
