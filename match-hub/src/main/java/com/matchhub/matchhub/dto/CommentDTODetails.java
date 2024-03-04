package com.matchhub.matchhub.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTODetails extends CommentDTOBaseId{
    private LocalDate creationDate;
    private LocalTime creationTime;
    private LocalDate updateDate;
    private LocalTime updateTime;
    private HubUserDTOLinks hubUser;
    private ScreenDTOLinks screen;
    private List<EvaluationDTOLinks> evaluations = new ArrayList<>();
}