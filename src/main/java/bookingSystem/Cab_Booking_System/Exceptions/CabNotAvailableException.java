package bookingSystem.Cab_Booking_System.Exceptions;

public class CabNotAvailableException extends RuntimeException {
    public CabNotAvailableException(String message) {
        super(message);
    }
}
