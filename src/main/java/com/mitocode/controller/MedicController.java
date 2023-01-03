package com.mitocode.controller;

import com.mitocode.dto.MedicDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
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
@RequestMapping("/medics")
public class MedicController {

    @Autowired
    private IMedicService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll(){
        List<MedicDTO> list = service.findAll()
                .stream()
                .map(p -> mapper.map(p, MedicDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id){
        MedicDTO dtoResponse;
        Medic obj = service.findById(id);
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            dtoResponse = mapper.map(obj, MedicDTO.class);
        }

        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MedicDTO dto){
        Medic p = service.save(mapper.map(dto, Medic.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getIdMedic())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<MedicDTO> update(@Valid @RequestBody MedicDTO dto){
        Medic obj = service.findById(dto.getIdMedic());
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdMedic());
        }
        return new ResponseEntity<>(mapper.map(service.update(mapper.map(dto, Medic.class)), MedicDTO.class) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Medic obj = service.findById(id);
        if(obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByIdHateoas(@PathVariable("id") Integer id){
        MedicDTO dtoResponse;
        Medic obj = service.findById(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        else {
            dtoResponse = mapper.map(obj, MedicDTO.class);
        }

        EntityModel<MedicDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        resource.add(link1.withRel("medic-info1"));
        resource.add(link2.withRel("medic-full"));

        return  resource;
    }
}
