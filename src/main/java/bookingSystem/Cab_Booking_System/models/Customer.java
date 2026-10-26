package bookingSystem.Cab_Booking_System.models;

import jakarta.persistence.*;
import jdk.jfr.Name;
import org.jspecify.annotations.NonNull;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "registration_dt")
    private LocalDateTime registrationDt;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    public void addBooking(Booking booking){
        bookings.add(booking);
        booking.setCustomer(this);
    }

    public void removeBooking(Booking booking){
        bookings.remove(booking);
        booking.setCustomer(null)   ;
    }

    public Customer(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getRegistrationDt() {
        return registrationDt;
    }

    public void setRegistrationDt(LocalDateTime registrationDt) {
        this.registrationDt = registrationDt;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
