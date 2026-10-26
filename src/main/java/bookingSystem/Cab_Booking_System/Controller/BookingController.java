package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.BookingRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.BookingResponseDTO;
import bookingSystem.Cab_Booking_System.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody  BookingRequestDTO dto){
        BookingResponseDTO created = bookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<BookingResponseDTO> completeBooking(@PathVariable Long id, @RequestParam BigDecimal fare){
        return ResponseEntity.ok(bookingService.completeBooking(id,fare));
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
