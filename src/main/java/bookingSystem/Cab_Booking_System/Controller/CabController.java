package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.CabRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CabResponseDTO;
import bookingSystem.Cab_Booking_System.Service.CabService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabs")
public class CabController {
    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @PostMapping
    public ResponseEntity<CabResponseDTO> addCab(@Valid @RequestBody CabRequestDTO dto){
        CabResponseDTO created = cabService.addCab(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping
    public ResponseEntity<List<CabResponseDTO>> getAllCabs(){
        return ResponseEntity.ok(cabService.getAllCabs());
    }

    @GetMapping("/availableCabs")
    public ResponseEntity<List<CabResponseDTO>> getAvailableCabs(){
        return ResponseEntity.ok(cabService.getAvailableCabs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabResponseDTO> getCabById(@PathVariable Long id){
       return ResponseEntity.ok(cabService.getCabById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabResponseDTO> updateCab(@PathVariable Long id, @Valid @RequestBody CabRequestDTO dto){
       return ResponseEntity.ok(cabService.updateCab(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCab(@PathVariable Long id){
        cabService.deleteCab(id);
        return ResponseEntity.noContent().build();
    }


}
