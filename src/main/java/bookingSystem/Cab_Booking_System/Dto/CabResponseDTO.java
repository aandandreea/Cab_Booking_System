package bookingSystem.Cab_Booking_System.Dto;

import bookingSystem.Cab_Booking_System.models.CabStatus;

public class CabResponseDTO {
    private Long id;
    private String registrationNo;
    private String model;
    private CabStatus status;
    private String driverName;

    public CabResponseDTO(Long id, String registrationNo, String model, CabStatus status, String driverName) {
        this.id = id;
        this.registrationNo = registrationNo;
        this.model = model;
        this.status = status;
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public CabStatus getStatus() {
        return status;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public Long getId() {
        return id;
    }

}
