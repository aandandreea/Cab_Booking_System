package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.CabRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CabResponseDTO;
import bookingSystem.Cab_Booking_System.Service.CabService;
import bookingSystem.Cab_Booking_System.models.CabStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CabController.class)
public class CabControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CabService cabService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addCab_withBlankRegistrationNo_returns400() throws Exception{
        CabRequestDTO dto = new CabRequestDTO();
        dto.setModel("BMW");
        dto.setRegistrationNo("");

        mockMvc.perform(post("/api/cabs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addCab_withValidData_returns201AndCallsService() throws Exception{
        CabRequestDTO dto = new CabRequestDTO();
        dto.setModel("BMW");
        dto.setRegistrationNo("B-09-TRG");

        CabResponseDTO fakeResponse = new CabResponseDTO(1L,dto.getRegistrationNo(),
                dto.getModel(), CabStatus.AVAILABLE, "Driver");

        when(cabService.addCab(any())).thenReturn(fakeResponse);

        mockMvc.perform(post("/api/cabs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registrationNo").value("B-09-TRG"));
    }

    @Test
    public void getCabById_withNonExistingId_returns404() throws Exception{

        when(cabService.getCabById(1L)).thenThrow(new EntityNotFoundException("Cab not found with this id: 1"));

        mockMvc.perform(get("/api/cabs/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCabs_returnsListOfCabs() throws Exception{
        CabRequestDTO dto = new CabRequestDTO();
        dto.setModel("BMW");
        dto.setRegistrationNo("B-09-TRG");

        CabResponseDTO fakeResponse = new CabResponseDTO(1L,dto.getRegistrationNo(),
                dto.getModel(), CabStatus.AVAILABLE, "Driver");

        when(cabService.getAllCabs()).thenReturn(List.of(fakeResponse));

        mockMvc.perform(get("/api/cabs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registrationNo").value("B-09-TRG"));
    }

    @Test
    public void deleteCab_returns204() throws Exception{
        mockMvc.perform(delete("/api/cabs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateCab_withBlankModel_returns400() throws Exception{
        CabRequestDTO dto = new CabRequestDTO();
        dto.setModel("");
        dto.setRegistrationNo("B-09-TRG");

        mockMvc.perform(put("/api/cabs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
