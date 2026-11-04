package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.BookingRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.BookingResponseDTO;
import bookingSystem.Cab_Booking_System.Service.BookingService;
import bookingSystem.Cab_Booking_System.models.BookingStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookingService bookingService;

    @Test
    public void createBooking_withBlankPickupLocation_returns400() throws Exception{
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setPickupLocation("");
        dto.setDropLocation("B");
        dto.setCabId(1L);
        dto.setCustomerId(1L);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBooking_withValidData_returns201AndCallsService() throws Exception{
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setPickupLocation("A");
        dto.setDropLocation("B");
        dto.setCabId(1L);
        dto.setCustomerId(1L);

        BookingResponseDTO fakeResponse = new BookingResponseDTO(1L,
                LocalDateTime.now(),
                dto.getPickupLocation(),
                dto.getDropLocation(),
                BigDecimal.valueOf(5),
                BookingStatus.COMPLETED,
                "User1",
                "B-08-TKU");

        when(bookingService.createBooking(any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cabRegistrationNo").value("B-08-TKU"));
    }

    @Test
    public void getBookingById_withNonExistingId_return404() throws Exception{
        when(bookingService.getBookingById(1L)).thenThrow(new EntityNotFoundException("Booking not found with id: 1"));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBookings_returnsListOfBookings() throws Exception{
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setPickupLocation("A");
        dto.setDropLocation("B");
        dto.setCabId(1L);
        dto.setCustomerId(1L);

        BookingResponseDTO fakeResponse = new BookingResponseDTO(1L,
                LocalDateTime.now(),
                dto.getPickupLocation(),
                dto.getDropLocation(),
                BigDecimal.valueOf(5),
                BookingStatus.COMPLETED,
                "User1",
                "B-08-TKU");

        when(bookingService.getAllBookings()).thenReturn(List.of(fakeResponse));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cabRegistrationNo").value("B-08-TKU"));
    }

    @Test
    public void completeBooking_returns200() throws Exception{
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setPickupLocation("A");
        dto.setDropLocation("B");
        dto.setCabId(1L);
        dto.setCustomerId(1L);

        BookingResponseDTO fakeResponse = new BookingResponseDTO(1L,
                LocalDateTime.now(),
                dto.getPickupLocation(),
                dto.getDropLocation(),
                BigDecimal.valueOf(5),
                BookingStatus.COMPLETED,
                "User1",
                "B-08-TKU");

        when(bookingService.completeBooking(1L,BigDecimal.valueOf(5))).thenReturn(fakeResponse);

        mockMvc.perform(put("/api/bookings/1/complete").param("fare","5"))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelBooking_throws200() throws Exception{
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setPickupLocation("A");
        dto.setDropLocation("B");
        dto.setCabId(1L);
        dto.setCustomerId(1L);

        BookingResponseDTO fakeResponse = new BookingResponseDTO(1L,
                LocalDateTime.now(),
                dto.getPickupLocation(),
                dto.getDropLocation(),
                BigDecimal.valueOf(5),
                BookingStatus.COMPLETED,
                "User1",
                "B-08-TKU");

        when(bookingService.cancelBooking(1L)).thenReturn(fakeResponse);

        mockMvc.perform(put("/api/bookings/1/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBooking_returns204() throws Exception{
        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }
}
