package bookingSystem.Cab_Booking_System.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class BookingRequestDTO {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Cab id is required")
    private Long cabId;

    @NotBlank(message = "Pickup location is required")
    @Size(max = 255, message = "Pickup location must be at most 255 characters.")
    private String pickupLocation;

    @NotBlank(message = "Drop location is required")
    @Size(max = 255, message = "Drop location must be at most 255 characters.")
    private String dropLocation;

    public BookingRequestDTO(Long customerId, Long cabId, String pickupLocation, String dropLocation) {
        this.customerId = customerId;
        this.cabId = cabId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
    }

    public BookingRequestDTO() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCabId() {
        return cabId;
    }

    public void setCabId(Long cabId) {
        this.cabId = cabId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }
}
