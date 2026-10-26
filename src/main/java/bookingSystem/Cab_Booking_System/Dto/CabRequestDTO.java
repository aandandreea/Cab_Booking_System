package bookingSystem.Cab_Booking_System.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CabRequestDTO {

    @NotBlank(message = "Registration number is required.")
    @Size(max = 50, message = "Registration number must be at most 50 characters.")
    private String registrationNo;

    @NotBlank(message = "Model is required.")
    @Size(max = 100, message = "Model must be at most 100 characters.")
    private String model;

    public CabRequestDTO() {
    }

    public CabRequestDTO(String registrationNo, String model) {
        this.registrationNo = registrationNo;
        this.model = model;
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
}
