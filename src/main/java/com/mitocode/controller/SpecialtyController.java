package com.mitocode.controller;

import com.mitocode.dto.SpecialtyDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Specialty;
import com.mitocode.service.ISpecialtyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private ISpecialtyService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll(){
        List<SpecialtyDTO> list = service.findAll()
                .stream()
                .map(p -> mapper.map(p, SpecialtyDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id){
        SpecialtyDTO dtoResponse;
        Specialty obj = service.findById(id);
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            dtoResponse = mapper.map(obj, SpecialtyDTO.class);
        }

        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SpecialtyDTO dto){
        Specialty p = service.save(mapper.map(dto, Specialty.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getIdSpecialty())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<SpecialtyDTO> update(@Valid @RequestBody SpecialtyDTO dto){
        Specialty obj = service.findById(dto.getIdSpecialty());
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdSpecialty());
        }
        return new ResponseEntity<>(mapper.map(service.update(mapper.map(dto, Specialty.class)), SpecialtyDTO.class) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Specialty obj = service.findById(id);
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByIdHateoas(@PathVariable("id") Integer id){
        SpecialtyDTO dtoResponse;
        Specialty obj = service.findById(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            dtoResponse = mapper.map(obj, SpecialtyDTO.class);
        }
        EntityModel<SpecialtyDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        resource.add(link1.withRel("specialty-info1"));
        resource.add(link2.withRel("specialty-full"));

        return  resource;
    }
}
