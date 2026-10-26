package bookingSystem.Cab_Booking_System.Exceptions;

public class LicenseNumberAlreadyExists extends RuntimeException{
    public LicenseNumberAlreadyExists(String message) {
        super(message);
    }
}
