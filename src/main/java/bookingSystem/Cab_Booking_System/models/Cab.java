package bookingSystem.Cab_Booking_System.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_no", nullable = false,unique = true, length = 50)
    private String registrationNo;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CabStatus status;

    @OneToMany(mappedBy = "cab")
    private List<Booking> bookings;

    @OneToOne(mappedBy = "cab")
    private Driver driver;


    public Cab(String registrationNo, String model, CabStatus status) {
        this.registrationNo = registrationNo;
        this.model = model;
        this.status = status;
    }

    public Cab() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CabStatus getStatus() {
        return status;
    }

    public void setStatus(CabStatus status) {
        this.status = status;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Driver getDriver() {
        return driver;
    }
}
