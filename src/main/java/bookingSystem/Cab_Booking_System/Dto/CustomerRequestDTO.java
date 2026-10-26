package bookingSystem.Cab_Booking_System.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be at most 100 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Size(max = 100, message = "Email must be at most 100 characters.")
    @Email(message = " Email must be valid.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 15, message = "Phone number must be at most 15 characters.")
    private String phoneNumber;

    public CustomerRequestDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CustomerRequestDTO() {
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
}
