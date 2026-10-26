package bookingSystem.Cab_Booking_System.Repository;

import bookingSystem.Cab_Booking_System.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    public boolean existsByLicenseNumber(String licenseNumber);
}
