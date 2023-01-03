package com.mitocode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultListExamDTO {

    //@JsonProperty(value = "nombre_atributo")
    @NotNull
    private ConsultDTO consult;

    @NotNull
    private List<ExamDTO> lstExam;
}
