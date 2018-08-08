package com.devinkin.service;

import com.devinkin.domain.Customer;

import java.util.List;

public interface CustomerService {
    public void save(Customer customer);

    public void update(Customer customer);

    public Customer getCustomerById(Long id);

    public List<Customer> findAll();

    public List<Customer> findAllByQBC();

    Customer loadById(long id);
}
