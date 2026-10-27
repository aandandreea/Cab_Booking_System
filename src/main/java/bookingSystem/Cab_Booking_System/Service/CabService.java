package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.CabRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CabResponseDTO;
import bookingSystem.Cab_Booking_System.Repository.CabRepository;
import bookingSystem.Cab_Booking_System.models.Cab;
import bookingSystem.Cab_Booking_System.models.CabStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class CabService {
    private static final Logger logger = LogManager.getLogger(CabService.class);

    private final CabRepository cabRepository;

    public CabService(CabRepository cabRepository) {
        this.cabRepository = cabRepository;
    }
    public CabResponseDTO addCab(CabRequestDTO dto){
        Cab cab = new Cab(dto.getRegistrationNo(),dto.getModel(), CabStatus.AVAILABLE);
        Cab saved = cabRepository.save(cab);

        logger.info("Adding a cab {}",saved.getId());
        return mapToResponseDTO(saved);
    }
    private CabResponseDTO mapToResponseDTO(Cab cab){
        String driverName = (cab.getDriver() != null) ? cab.getDriver().getName() : null;
        return new CabResponseDTO(cab.getId(), cab.getRegistrationNo(), cab.getModel(), cab.getStatus(), driverName);
    }

    public List<CabResponseDTO> getAllCabs(){
       return cabRepository.findAll()
               .stream()
               .map(this::mapToResponseDTO)
               .toList();
    }
    // alta varianta pt mapare
   /* public List<Cab> getAllCab(){
        List<Cab> list = cabRepository.findAll();
        List<CabResponseDTO> result = new ArrayList<>();
        for(Cab cab : list){
            result.add(cab);
        }
        return result;
        }
    */

    public CabResponseDTO getCabById(Long id){
        Cab cab = cabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cab not found with id: " + id));
        return mapToResponseDTO(cab);
    }

    public List<CabResponseDTO> getAvailableCabs(){
        return cabRepository.findByStatus(CabStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public CabResponseDTO updateCab(Long id, CabRequestDTO dto) {
        Cab cab = cabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cab not found with this id" + id));
        cab.setRegistrationNo(dto.getRegistrationNo());
        cab.setModel(dto.getModel());
        Cab updated = cabRepository.save(cab);
        return mapToResponseDTO(updated);
    }

        public void deleteCab(Long id){
            Cab cab = cabRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cab not found with this id" + id));
            cabRepository.deleteById(id);

        }
}
