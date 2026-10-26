package bookingSystem.Cab_Booking_System.models;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "license_number",nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    @OneToOne
    @JoinColumn(name = "cab_id", referencedColumnName = "id")
    private Cab cab;

    public Driver(String name, String phoneNumber, String licenceNumber, BigDecimal rating, Cab cab) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenceNumber;
        this.rating = rating;
        this.cab = cab;
    }

    public Driver() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenceNumber() {
        return licenseNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenseNumber = licenceNumber;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }


}
