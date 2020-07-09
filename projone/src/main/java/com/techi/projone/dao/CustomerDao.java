package com.techi.projone.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techi.projone.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
