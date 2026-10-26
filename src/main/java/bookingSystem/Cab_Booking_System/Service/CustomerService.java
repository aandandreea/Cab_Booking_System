package bookingSystem.Cab_Booking_System.Service;

import bookingSystem.Cab_Booking_System.Dto.CustomerRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CustomerResponseDTO;
import bookingSystem.Cab_Booking_System.Exceptions.EmailAlreadyExistsException;
import bookingSystem.Cab_Booking_System.Repository.CustomerRepository;
import bookingSystem.Cab_Booking_System.models.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto){
        if(customerRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistsException("Email already in use" + dto.getEmail());
        }
        Customer customer = new Customer(dto.getName(), dto.getEmail(), dto.getPhoneNumber());
        customer.setRegistrationDt(LocalDateTime.now());
        Customer saved = customerRepository.save(customer);
        return mapToResponseDTO(saved);

    }
    public List<CustomerResponseDTO> getAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    public CustomerResponseDTO getCustomerById(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with this id:" + id));
        return mapToResponseDTO(customer);
    }
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with this id:" + id));
        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        Customer updated = customerRepository.save(customer);
        return mapToResponseDTO(updated);
    }
    public void deleteCustomer(Long id){
        if(!customerRepository.existsById(id)){
                throw new EntityNotFoundException("Customer not found with this id:" + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getRegistrationDt()
        );
    }
}
