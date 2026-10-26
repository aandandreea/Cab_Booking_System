package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.DriverRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.DriverResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.LicenseNumberAlreadyExists;
import bookingSystem.Cab_Booking_System.Repository.DriverRepository;
import bookingSystem.Cab_Booking_System.models.Driver;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public DriverResponseDTO registerDriver(DriverRequestDTO dto){
        if(driverRepository.existsByLicenseNumber(dto.getLicenseNumber())){
            throw new LicenseNumberAlreadyExists("License number already in use " + dto.getLicenseNumber());
        }
        Driver driver = new Driver(dto.getName(),dto.getPhoneNumber(),dto.getLicenseNumber(),null,null);
        Driver saved = driverRepository.save(driver);
        return mapToResponseDTO(saved);
    }
    public List<DriverResponseDTO> getAllDrivers(){
        List<Driver>drivers = driverRepository.findAll();
        List<DriverResponseDTO> result = new ArrayList<>();

        for(Driver driver : drivers){
            result.add(mapToResponseDTO(driver));
        }
        return result;
    }
    public DriverResponseDTO getDriverById(Long id){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Driver not found with this id " + id));

        return mapToResponseDTO(driver);
    }
    public DriverResponseDTO updateDriver(Long id, DriverRequestDTO dto){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Driver not found with this id " + id));
        driver.setName(dto.getName());
        driver.setPhoneNumber(dto.getPhoneNumber());
        Driver updated = driverRepository.save(driver);
        return mapToResponseDTO(updated);
    }
    public void deleteDriver(Long id){
        if(!driverRepository.existsById(id)){
            throw new EntityNotFoundException("Driver not found with this id " + id);
        }
        driverRepository.deleteById(id);
    }
    public DriverResponseDTO mapToResponseDTO(Driver driver){
        String cabRegistrationNo = (driver.getCab() != null) ? driver.getCab().getRegistrationNo() : null;
        return new DriverResponseDTO(
                driver.getId(),
                driver.getName(),
                driver.getPhoneNumber(),
                driver.getLicenceNumber(),
                driver.getRating(),
                cabRegistrationNo
        );
    }
}
