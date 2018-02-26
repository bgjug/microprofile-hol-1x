package bg.jug.microprofile.hol.subscribers;

import java.time.LocalDate;

/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
public class Subscriber {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate subscribedUntil;

    public Subscriber(){
        //empty constructor
    }

    public Subscriber(String firstName, String lastName, String email, String address, LocalDate subscribedUntil) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.subscribedUntil = subscribedUntil;
    }

    public Subscriber(Long id, String firstName, String lastName, String email, String address, LocalDate subscribedUntil) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.subscribedUntil = subscribedUntil;
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


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public LocalDate getSubscribedUntil() {
        return subscribedUntil;
    }


    public void setSubscribedUntil(LocalDate subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;

        Subscriber that = (Subscriber) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return subscribedUntil != null ? subscribedUntil.equals(that.subscribedUntil) : that.subscribedUntil == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (subscribedUntil != null ? subscribedUntil.hashCode() : 0);
        return result;
    }
}
