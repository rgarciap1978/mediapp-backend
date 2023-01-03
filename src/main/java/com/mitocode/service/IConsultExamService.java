package com.mitocode.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.mitocode.model.ConsultExam;

public interface IConsultExamService {

	List<ConsultExam> getExamsByConsultId(@Param("idConsult") Integer idConsult);
}
