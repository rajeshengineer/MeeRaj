package com.techi.projone.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techi.projone.dao.CustomerDao;
import com.techi.projone.exception.RecordNotFoundException;
import com.techi.projone.helper.ExcelHelper;
import com.techi.projone.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao repository;

	@Override
	public List<Customer> listCustomers() throws RecordNotFoundException {

		List<Customer> custList = repository.findAll();
		if (custList.isEmpty()) {
			throw new RecordNotFoundException("No Customer record exist");
		} else {
			return custList;
		}
	}

	@Override
	public Customer getCustomerById(long id) throws RecordNotFoundException {

		{
			Optional<Customer> customer = repository.findById(id);

			if (customer.isPresent()) {
				return customer.get();
			} else {
				throw new RecordNotFoundException("No Customer record exist for given id");
			}
		}
	}

	@Override
	public void removeCustomer(Long id) throws RecordNotFoundException {

		Optional<Customer> cust = repository.findById(id);

		if (cust.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	@Override
	public Customer addOrUpdateCustomer(Customer cust) {

		Optional<Customer> customer = repository.findById((long) cust.getId());

		if (customer.isPresent()) {
			Customer newEntity = customer.get();
			newEntity.setAddrs(cust.getAddrs());
			newEntity.setFname(cust.getFname());
			newEntity.setLname(cust.getLname());
			newEntity.setPnum(cust.getPnum());
			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			cust = repository.save(cust);

			return cust;
		}

	}

	@Override
	public void bulkload(MultipartFile excelFile) throws IOException {
		try {
			List<Customer> custlist = ExcelHelper.excelToCustomer(excelFile.getInputStream());
			repository.saveAll(custlist);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
