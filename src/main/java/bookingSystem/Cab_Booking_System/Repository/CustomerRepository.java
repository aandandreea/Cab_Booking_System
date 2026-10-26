package bookingSystem.Cab_Booking_System.Repository;

import bookingSystem.Cab_Booking_System.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
