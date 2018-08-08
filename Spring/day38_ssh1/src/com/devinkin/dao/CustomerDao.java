package com.devinkin.dao;


import com.devinkin.domain.Customer;

import java.util.List;

public interface CustomerDao {
    public void save(Customer customer);

    void update(Customer customer);

    Customer getById(Long id);

    List<Customer> findAll();

    List<Customer> findAllByQBC();

    Customer loadById(long id);
}
