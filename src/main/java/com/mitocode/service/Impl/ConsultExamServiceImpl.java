package com.mitocode.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.ConsultExam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.service.IConsultExamService;

@Service
public class ConsultExamServiceImpl implements IConsultExamService {

	@Autowired
	private IConsultExamRepo repo;
	
	@Override
	public List<ConsultExam> getExamsByConsultId(Integer idConsult) {
		return repo.getExamsByConsulId(idConsult);
	}

}
