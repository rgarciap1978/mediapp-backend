package com.mitocode.service.Impl;

import com.mitocode.model.Patient;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPatientRepo;
import com.mitocode.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

    @Autowired
    private IPatientRepo repo;

    @Override
    protected IGenericRepo<Patient, Integer> getRepo() {
        return repo;
    }

	@Override
	public Page<Patient> listPage(Pageable page) {
		return repo.findAll(page);
	}

    /*
    @Override
    public Patient save(Patient patient) {
        return repo.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        return repo.save(patient);
    }

    @Override
    public Patient findById(Integer id) {
        Optional<Patient> op = repo.findById(id);

        // Las siguientes lineas hacen lo mismo
        //return op.isPresent()?op.get():new Patient();
        return op.orElseGet(Patient::new);
    }

    @Override
    public List<Patient> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
     */
}
