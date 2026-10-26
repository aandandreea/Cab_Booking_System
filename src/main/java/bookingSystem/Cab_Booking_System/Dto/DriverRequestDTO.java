package bookingSystem.Cab_Booking_System.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class DriverRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must be at most 15 characters")
    private String phoneNumber;

    @NotBlank(message = "License number is required")
    @Size(max = 50, message = "License number must be at most 50 characters")
    private String licenseNumber;

    public DriverRequestDTO() {
    }

    public DriverRequestDTO(String name, String phoneNumber, String licenseNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
