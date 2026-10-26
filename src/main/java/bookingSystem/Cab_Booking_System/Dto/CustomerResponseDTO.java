package bookingSystem.Cab_Booking_System.Dto;

import java.time.LocalDateTime;

public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime registrationDt;

    public CustomerResponseDTO(Long id, String name, String email, String phoneNumber, LocalDateTime registrationDt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationDt = registrationDt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getRegistrationDt() {
        return registrationDt;
    }
}
