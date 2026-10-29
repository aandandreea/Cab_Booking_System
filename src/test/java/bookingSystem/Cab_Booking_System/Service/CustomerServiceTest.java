package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.CustomerRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CustomerResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.EmailAlreadyExistsException;
import bookingSystem.Cab_Booking_System.Repository.CustomerRepository;
import bookingSystem.Cab_Booking_System.models.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void addCustomer_withAnExistingEmail_trowsException(){
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setEmail("user1@gmail.com");
        dto.setName("User2");
        dto.setPhoneNumber("0783921782");

        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(true);
         assertThrows(EmailAlreadyExistsException.class, () -> customerService.registerCustomer(dto));
    }

    @Test
    public void registerCustomer_withUniqueEmail(){
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setEmail("user3@gmail.com");
        dto.setName("User3");
        dto.setPhoneNumber("0783921783");


        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CustomerResponseDTO result = customerService.registerCustomer(dto);

        assertEquals("User3",dto.getName());
        assertEquals("user3@gmail.com", dto.getEmail());
        assertNotNull(result.getRegistrationDt());
    }

    @Test
    public void getCustomerById_withNonExistingId(){

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
    }
}
