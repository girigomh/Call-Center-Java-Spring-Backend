package org.comcom.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comcom.dto.ApiResponse;
import org.comcom.service.AppointmentService;
import org.comcom.dto.AppointmentDto;
import org.comcom.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;

import static org.comcom.utils.ApiResponseUtils.buildResponse;

@CrossOrigin
@RestController
@RequestMapping("/v1/appointments")
@Slf4j(topic = "AppointmentController")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public  AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    ApiResponse<Appointment> createAppointment(@RequestBody @Valid AppointmentDto appointmentDto){
        return buildResponse(appointmentService.createAppointment(appointmentDto));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> getAppointmentById(@PathVariable Long id){
        return buildResponse(appointmentService.getAppointmentById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> getAllAppointments(){
        return buildResponse(appointmentService.getAllAppointments());
    }

    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto){
        return buildResponse(appointmentService.updateAppointment(id, appointmentDto));
    }

    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> deleteAppointment(@PathVariable Long id){
        return buildResponse(appointmentService.deleteAppointment(id));
    }

}
