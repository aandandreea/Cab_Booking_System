package bookingSystem.Cab_Booking_System.Controller;

import bookingSystem.Cab_Booking_System.Dto.CustomerRequestDTO;
import bookingSystem.Cab_Booking_System.Dto.CustomerResponseDTO;
import bookingSystem.Cab_Booking_System.Service.CustomerService;
import bookingSystem.Cab_Booking_System.models.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@Valid @RequestBody CustomerRequestDTO dto){
        CustomerResponseDTO saved = customerService.registerCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto){
        return ResponseEntity.ok(customerService.updateCustomer(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
