package com.techi.projone.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techi.projone.exception.RecordNotFoundException;
import com.techi.projone.exceptions.ResponseMessage;
import com.techi.projone.helper.ExcelHelper;
import com.techi.projone.model.Customer;
import com.techi.projone.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class PersonController {

	@Autowired
	CustomerService service;
	private static Logger logger = LoggerFactory.getLogger(PersonController.class);

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() throws RecordNotFoundException {
		logger.info("Request came to display all the records");
		List<Customer> list = service.listCustomers();
		return new ResponseEntity<List<Customer>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Customer entity = service.getCustomerById(id);
		logger.info("Request came to display the records of " + id);
		return new ResponseEntity<Customer>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Customer> createOrUpdateEmployee(@RequestBody Customer cust) throws RecordNotFoundException {
		Customer updated = service.addOrUpdateCustomer(cust);
		logger.info("Request came to display the records of " + cust);
		return new ResponseEntity<Customer>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteCustomerById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.removeCustomer(id);
		return HttpStatus.FORBIDDEN;
	}

	@PostMapping("/import")
	public ResponseEntity<ResponseMessage> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile ExcelFile)
			throws IOException {

		String message;
		if (ExcelHelper.hasExcelFormat(ExcelFile)) {
			try {
				service.bulkload(ExcelFile);

				message = "Uploaded the file successfully: " + ExcelFile.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + ExcelFile.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		} else {
			message = "Please upload an excel file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
		}
	}

}
