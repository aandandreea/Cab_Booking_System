package bookingSystem.Cab_Booking_System.Repository;

import bookingSystem.Cab_Booking_System.models.Cab;
import bookingSystem.Cab_Booking_System.models.CabStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabRepository extends JpaRepository<Cab,Long> {
    List<Cab> findByStatus(CabStatus status);
}
