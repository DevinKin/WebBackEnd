package cn.devinkin.service;


import cn.devinkin.pojo.BaseDict;
import cn.devinkin.pojo.Customer;
import cn.devinkin.pojo.QueryVo;

import java.util.List;

public interface CustomerService {
    public List<BaseDict> findDictByCode(String code);

    public List<Customer> findCustomerByVo(QueryVo vo);

    public Integer findCustomerByVoCount(QueryVo vo);

    public Customer findCustomerById(Long id);

    public void updateCustomerById(Customer customer);

    public void deleteCustomerById(Long id);
}
