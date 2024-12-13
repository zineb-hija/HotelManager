package org.example.hotelmanage.service;

import org.example.hotelmanage.model.Reservation;
import org.example.hotelmanage.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setClientName(updatedReservation.getClientName());
                    reservation.setRoomPreference(updatedReservation.getRoomPreference());
                    reservation.setCheckInDate(updatedReservation.getCheckInDate());
                    reservation.setCheckOutDate(updatedReservation.getCheckOutDate());
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

