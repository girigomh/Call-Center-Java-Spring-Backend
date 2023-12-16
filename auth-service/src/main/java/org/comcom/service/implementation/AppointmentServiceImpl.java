package org.comcom.service.implementation;

import lombok.AllArgsConstructor;
import org.comcom.exception.NotFoundException;
import org.comcom.service.AppointmentService;
import org.comcom.repository.AppointmentRepository;
import org.comcom.dto.AppointmentDto;
import org.comcom.model.Appointment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDto, appointment);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not Found with id:" + id));
    }

    @Override
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @Override
    @Transactional
    public Appointment updateAppointment(Long id, AppointmentDto appointmentDto) {
        Appointment existingAppointment = getAppointmentById(id);
        BeanUtils.copyProperties(appointmentDto, existingAppointment);
        return existingAppointment;
    }

    @Override
    @Transactional
    public boolean deleteAppointment(Long id){
        if(appointmentRepository.existsById(id)){
            appointmentRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Appointment not found", "Appointment not found: "+id);
    }
}
