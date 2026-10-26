package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.DriverRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.DriverResponseDTO;
import bookingSystem.Cab_Booking_System.Service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> registerDriver(@Valid @RequestBody DriverRequestDTO dto){
        DriverResponseDTO saved = driverService.registerDriver(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers(){
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverRequestDTO dto){
        return ResponseEntity.ok(driverService.updateDriver(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
