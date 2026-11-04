package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.DriverRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.DriverResponseDTO;
import bookingSystem.Cab_Booking_System.Service.DriverService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private DriverService driverService;

    @Test
    public void registerDriver_withBlankName_return400() throws Exception{
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setLicenseNumber("IF-08-FGD");
        dto.setName("");
        dto.setPhoneNumber("0812345369");

        mockMvc.perform(post("/api/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerDriver_withValidData_returns201AndCallsService() throws Exception{
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setLicenseNumber("IF-08-FGD");
        dto.setName("User");
        dto.setPhoneNumber("0812345369");

        DriverResponseDTO fakeResponse = new DriverResponseDTO(1L,
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getLicenseNumber(),
                BigDecimal.valueOf(4.2),
                "IF-08-FGD");

        when(driverService.registerDriver(any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/api/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
    @Test
    public void getDriverById_withNonExistingId_returns404() throws Exception{
        when(driverService.getDriverById(1L)).thenThrow(new EntityNotFoundException("Driver not found with this id: 1"));

        mockMvc.perform(get("/api/drivers/1"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getAllDrivers_returnsListOfDrivers() throws Exception{
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setLicenseNumber("IF-08-FGD");
        dto.setName("User");
        dto.setPhoneNumber("0812345369");

        DriverResponseDTO fakeResponse = new DriverResponseDTO(1L,
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getLicenseNumber(),
                BigDecimal.valueOf(4.2),
                "IF-08-FGD");

        when(driverService.getAllDrivers()).thenReturn(List.of(fakeResponse));

        mockMvc.perform(get("/api/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licenseNumber").value("IF-08-FGD"));
    }

    @Test
    public void deleteDriver_return204() throws Exception{
        mockMvc.perform(delete("/api/drivers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateDriver_withBlankName_returns400() throws Exception{
        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setLicenseNumber("IF-08-FGD");
        dto.setName("");
        dto.setPhoneNumber("0812345369");

        mockMvc.perform(put("/api/drivers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


}
