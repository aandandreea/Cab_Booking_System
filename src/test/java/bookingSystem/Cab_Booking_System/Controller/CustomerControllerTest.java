package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.CustomerRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CustomerResponseDTO;
import bookingSystem.Cab_Booking_System.Service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerCustomer_withBlankName_returns400() throws Exception {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("");
        dto.setEmail("user@gmail.com");
        dto.setPhoneNumber("0745123456");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerCustomer_withValidData_returns201andCallService() throws Exception{
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("user2");
        dto.setEmail("user2@gmail.com");
        dto.setPhoneNumber("0712345678");

        CustomerResponseDTO fakeResponse = new CustomerResponseDTO(1L,
                dto.getName(), dto.getEmail(), dto.getPhoneNumber(), LocalDateTime.now());

        when(customerService.registerCustomer(any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("user2"));
    }

    @Test
    public void getCustomerById_withNonExistingId_returns404() throws Exception{
        when(customerService.getCustomerById(1L)).thenThrow(new EntityNotFoundException("Customer not found with id: 1"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCustomers_returnsListOfCustomers() throws Exception{
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("user2");
        dto.setEmail("user2@gmail.com");
        dto.setPhoneNumber("0712345678");

        CustomerResponseDTO fakeResponse = new CustomerResponseDTO(1L,
                dto.getName(), dto.getEmail(), dto.getPhoneNumber(), LocalDateTime.now());

        when(customerService.getAllCustomers()).thenReturn(List.of(fakeResponse));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("user2"));
    }
    @Test
    public void updateCustomer_withBlankName_returns400() throws Exception{
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("");
        dto.setEmail("user2@gmail.com");
        dto.setPhoneNumber("0712345678");

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCustomer_returns204() throws Exception{
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
    }

}
