package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.CabResponseDTO;
import bookingSystem.Cab_Booking_System.Repository.CabRepository;
import bookingSystem.Cab_Booking_System.models.Cab;
import bookingSystem.Cab_Booking_System.models.CabStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CabServiceTest {
    @Mock
    private CabRepository cabRepository;

    @InjectMocks
    private CabService cabService;

    @Test
    public void getCabById_withNonExistingId(){
        when(cabRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cabService.getCabById(1L));
    }

    @Test
    public void getAvailableCabs_returnsOnlyAvailableCabs(){
        Cab cab1 = new Cab("B-10-IOP", "Mercedes", CabStatus.AVAILABLE);
        Cab cab2 = new Cab("VL-56-REK","Audi", CabStatus.AVAILABLE);

        when(cabRepository.findByStatus(CabStatus.AVAILABLE)).thenReturn(List.of(cab1,cab2));

        List<CabResponseDTO> result = cabService.getAvailableCabs();

        assertEquals(2,result.size());
    }
}
