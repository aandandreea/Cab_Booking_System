package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.DriverRequestDTO;

import bookingSystem.Cab_Booking_System.Dto.DriverResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.LicenseNumberAlreadyExists;
import bookingSystem.Cab_Booking_System.Repository.DriverRepository;

import bookingSystem.Cab_Booking_System.models.Driver;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    public void registerDriver_withExistingLicense_throwsException(){
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setName("User1");
        dto.setPhoneNumber("0745629189");
        dto.setLicenseNumber("B-02-GFO");

        when(driverRepository.existsByLicenseNumber(dto.getLicenseNumber())).thenReturn(true);
        assertThrows(LicenseNumberAlreadyExists.class, () -> driverService.registerDriver(dto));

    }

    @Test
    public void registerDriver_withUniqueLicense(){
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setName("User1");
        dto.setPhoneNumber("0745629174");
        dto.setLicenseNumber("B-02-GFO");

        when(driverRepository.existsByLicenseNumber(dto.getLicenseNumber())).thenReturn(false);
        when(driverRepository.save(any(Driver.class))).thenAnswer(invocation-> invocation.getArgument(0));

        DriverResponseDTO result = driverService.registerDriver(dto);

        assertEquals("User1", dto.getName());
        assertEquals("0745629174", dto.getPhoneNumber());
        assertEquals("B-02-GFO", dto.getLicenseNumber());
    }

    @Test
    public void getDriverById_withNonExistingId(){
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> driverService.getDriverById(1L));
    }
}
