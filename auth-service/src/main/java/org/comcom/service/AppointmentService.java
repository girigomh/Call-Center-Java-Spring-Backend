package org.comcom.service;

import org.comcom.dto.*;
import org.comcom.model.Appointment;
import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(AppointmentDto appointmentDto);
    Appointment getAppointmentById(Long id);
    List<Appointment> getAllAppointments();
    Appointment updateAppointment(Long id, AppointmentDto appointmentDto);
    boolean deleteAppointment(Long id);
}
