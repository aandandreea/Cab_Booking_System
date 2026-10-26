package bookingSystem.Cab_Booking_System.Dto;

import bookingSystem.Cab_Booking_System.models.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingResponseDTO {
    private Long id;
    private LocalDateTime bookingTime;
    private String pickupLocation;
    private String dropLocation;
    private BigDecimal fare;
    private BookingStatus status;
    private String customerName;
    private String cabRegistrationNo;

    public BookingResponseDTO(Long id, LocalDateTime bookingTime, String pickupLocation, String dropLocation, BigDecimal fare, BookingStatus status, String customerName, String cabRegistrationNo) {
        this.id = id;
        this.bookingTime = bookingTime;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.fare = fare;
        this.status = status;
        this.customerName = customerName;
        this.cabRegistrationNo = cabRegistrationNo;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCabRegistrationNo() {
        return cabRegistrationNo;
    }
}
