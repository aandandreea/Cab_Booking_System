package bookingSystem.Cab_Booking_System.Dto;

import java.math.BigDecimal;

public class DriverResponseDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private BigDecimal rating;
    private String cabRegistrationNo;

    public DriverResponseDTO(Long id, String name, String phoneNumber, String licenseNumber, BigDecimal rating, String cabRegistrationNo) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.rating = rating;
        this.cabRegistrationNo = cabRegistrationNo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public String getCabRegistrationNo() {
        return cabRegistrationNo;
    }
}
