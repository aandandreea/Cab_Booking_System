package bookingSystem.Cab_Booking_System.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookingSystem.Cab_Booking_System.Dto.BookingRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.BookingResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.CabNotAvailableException;
import bookingSystem.Cab_Booking_System.Repository.BookingRepository;
import bookingSystem.Cab_Booking_System.Repository.CabRepository;
import bookingSystem.Cab_Booking_System.Repository.CustomerRepository;
import bookingSystem.Cab_Booking_System.models.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookingService {
    private static final Logger logger = LogManager.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final CabRepository cabRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, CabRepository cabRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.cabRepository = cabRepository;
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto){
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(()-> new EntityNotFoundException("Customer not found with this id " + dto.getCustomerId()));
        Cab cab = cabRepository.findById(dto.getCabId())
                .orElseThrow(()-> new EntityNotFoundException("Cab not found with this id " + dto.getCabId()));

        logger.info("Creating booking for customer {} and cab {}", dto.getCustomerId(), dto.getCabId());

        if(cab.getStatus() != CabStatus.AVAILABLE){
            logger.warn("Cab {} not available for booking.", dto.getCabId());
            throw new CabNotAvailableException("Cab is not available.");
        }

        Booking booking = new Booking(dto.getPickupLocation(),
                dto.getDropLocation(),
                BigDecimal.ZERO,
                BookingStatus.CONFIRMED,
                customer,
                cab);
        booking.setBookingTime(LocalDateTime.now());
        cab.setStatus(CabStatus.BOOKED);
        cabRepository.save(cab);

        Booking saved = bookingRepository.save(booking);

        logger.info("Booking {} created successfully",saved.getId());

        return mapToResponseDto(saved);
    }

    public List<BookingResponseDTO> getAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> result = new ArrayList<>();

        for(Booking booking : bookings){
            result.add(mapToResponseDto(booking));
        }
        return result;
    }

    public BookingResponseDTO getBookingById(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with this id " + id));
        return mapToResponseDto(booking);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with this id " + id));

        logger.info("Canceling a booking {}",booking.getId());

        booking.setStatus(BookingStatus.CANCELLED);
        booking.getCab().setStatus(CabStatus.AVAILABLE);
        cabRepository.save(booking.getCab());
        Booking updated = bookingRepository.save(booking);
        return mapToResponseDto(updated);
    }

    @Transactional
    public BookingResponseDTO completeBooking(Long id, BigDecimal fare){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Booking not found with this id " + id));
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setFare(fare);
        booking.getCab().setStatus(CabStatus.AVAILABLE);
        cabRepository.save(booking.getCab());
        Booking updated = bookingRepository.save(booking);
        return mapToResponseDto(updated);
    }

    public void deleteBooking(Long id){
        if(!bookingRepository.existsById(id)){
            throw new EntityNotFoundException("Booking not found with this id " + id);
        }
        bookingRepository.deleteById(id);
    }

    private BookingResponseDTO mapToResponseDto(Booking booking){
        return new BookingResponseDTO(booking.getId(),
                booking.getBookingTime(),
                booking.getPickupLocation(),
                booking.getDropLocation(),
                booking.getFare(),
                booking.getStatus(),
                booking.getCustomer().getName(),
                booking.getCab().getRegistrationNo()
                );
    }
}
