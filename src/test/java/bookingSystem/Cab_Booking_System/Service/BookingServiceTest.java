package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.BookingRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.BookingResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.CabNotAvailableException;
import bookingSystem.Cab_Booking_System.Repository.BookingRepository;
import bookingSystem.Cab_Booking_System.Repository.CabRepository;
import bookingSystem.Cab_Booking_System.Repository.CustomerRepository;
import bookingSystem.Cab_Booking_System.models.Booking;
import bookingSystem.Cab_Booking_System.models.BookingStatus;
import bookingSystem.Cab_Booking_System.models.Cab;
import bookingSystem.Cab_Booking_System.models.CabStatus;
import bookingSystem.Cab_Booking_System.models.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CabRepository cabRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void createBooking_whenCabNotAvailable_throwsException(){
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setCustomerId(1L);
        dto.setCabId(1L);
        dto.setPickupLocation("A");
        dto.setDropLocation("B");

        Customer customer = new Customer();
        Cab cab = new Cab();
        cab.setStatus(CabStatus.BOOKED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cabRepository.findById(1L)).thenReturn(Optional.of(cab));

        assertThrows(CabNotAvailableException.class, () -> bookingService.createBooking(dto));
    }

    @Test
    public void createBooking_cabAvailable_createsBookingSuccessfully(){
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setCustomerId(1L);
        dto.setCabId(1L);
        dto.setPickupLocation("A");
        dto.setDropLocation("B");

        Customer customer = new Customer();
        customer.setName("User1");

        Cab cab = new Cab();
        cab.setStatus(CabStatus.AVAILABLE);
        cab.setRegistrationNo("B-100-XYZ");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cabRepository.findById(1L)).thenReturn(Optional.of(cab));

        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponseDTO result = bookingService.createBooking(dto);

        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        assertEquals(CabStatus.BOOKED, cab.getStatus());
        verify(cabRepository).save(cab);
    }

    @Test
    public void cancelBooking_setsStatusCancelled_andFreesCab(){
        Cab cab = new Cab();
        cab.setStatus(CabStatus.BOOKED);
        cab.setRegistrationNo("B-10-XOP");

        Customer customer = new Customer();
        customer.setName("User2");

        Booking booking = new Booking("A", "B", BigDecimal.ZERO, BookingStatus.CONFIRMED, customer, cab);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponseDTO result = bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        assertEquals(CabStatus.AVAILABLE, cab.getStatus());
        verify(cabRepository).save(cab);
    }

    @Test
    public void completeBooking_setsStatusCompletedAndFare_andFreesCab(){
        Cab cab = new Cab();
        cab.setStatus(CabStatus.BOOKED);
        cab.setRegistrationNo("B-19-VGT");

        Customer customer = new Customer();
        customer.setName("User3");

        Booking booking = new Booking("A", "B", BigDecimal.ZERO, BookingStatus.CONFIRMED, customer, cab);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal fare = new BigDecimal("25.50");
        BookingResponseDTO result = bookingService.completeBooking(1L, fare);

        assertEquals(BookingStatus.COMPLETED, result.getStatus());
        assertEquals(fare, result.getFare());
        assertEquals(CabStatus.AVAILABLE, cab.getStatus());
        verify(cabRepository).save(cab);
    }
}
