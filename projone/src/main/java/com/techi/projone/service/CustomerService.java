package com.techi.projone.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techi.projone.exception.RecordNotFoundException;
import com.techi.projone.model.Customer;

public interface CustomerService {

	public Customer addOrUpdateCustomer(Customer p);

	public List<Customer> listCustomers() throws RecordNotFoundException;

	public Customer getCustomerById(long id) throws RecordNotFoundException;

	public void removeCustomer(Long id) throws RecordNotFoundException;

	public void bulkload(MultipartFile excelFile) throws IOException;

}
