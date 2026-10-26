package bookingSystem.Cab_Booking_System.Repository;

import bookingSystem.Cab_Booking_System.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
