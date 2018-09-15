package cn.devinkin.dao;

import cn.devinkin.pojo.Customer;
import cn.devinkin.pojo.QueryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
    public List<Customer> findCustomerByVo(QueryVo vo);

    public Integer findCustomerByVoCount(QueryVo vo);

    public Customer findCustomerById(Long id);

    public void updateCustomerById(Customer customer);

    public void deleteCustomerById(Long id);
}
