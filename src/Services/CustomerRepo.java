package Services;

import Entity.Customer;
import Generic.IService;

import java.util.List;
import java.util.Optional;

public class CustomerRepo implements IService<Customer> {
    public static List<Customer> customers;
    public CustomerRepo() {;}

    @Override
    public Customer add() {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public Optional<Customer> findById(String id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}
