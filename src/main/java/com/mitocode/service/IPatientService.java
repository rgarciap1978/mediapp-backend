package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Patient;

public interface IPatientService extends ICRUD<Patient, Integer> {

	Page<Patient> listPage(Pageable page);
}
