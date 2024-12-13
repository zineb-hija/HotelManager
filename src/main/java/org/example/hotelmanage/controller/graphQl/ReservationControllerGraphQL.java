package org.example.hotelmanage.controller.graphQl;

import lombok.AllArgsConstructor;

import org.example.hotelmanage.model.Reservation;

import org.example.hotelmanage.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@AllArgsConstructor
public class ReservationControllerGraphQL {

    @Autowired
    private ReservationRepository reservationRepository;

    // Récupérer toutes les réservations
    @QueryMapping
    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    @QueryMapping
    public Reservation reservationById(@Argument Long id) {
        Reservation reservation= reservationRepository.findById(id).orElse(null);
        if(reservation == null) throw new RuntimeException(String.format("Reservation %s not found", id));
        else return reservation;
    }

    // Ajouter ou mettre à jour une réservation
    @MutationMapping
    public Reservation saveReservation(@Argument Reservation reservation){
        return reservationRepository.save(reservation);

    }

    // Supprimer une réservation par son ID
    @MutationMapping
    public String deleteReservation(@Argument Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation with ID " + id + " not found");
        }
        reservationRepository.deleteById(id);
        return "Reservation with ID " + id + " has been successfully deleted.";
    }

    @MutationMapping
    public Reservation updateReservation(
            @Argument Long id,
            @Argument String clientName,
            @Argument String checkInDate,
            @Argument String checkOutDate,
            @Argument String roomPreference) {

        // Conversion des chaînes de caractères en objets Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start;
        Date end;

        try {
            start = dateFormat.parse(checkInDate);
            end = dateFormat.parse(checkOutDate);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Please use 'yyyy-MM-dd'.");
        }

        // Vérifiez si la réservation existe
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with ID " + id + " not found"));

        // Mettez à jour les champs de la réservation existante
        existingReservation.setClientName(clientName);
        existingReservation.setCheckInDate(String.valueOf(start));
        existingReservation.setCheckOutDate(String.valueOf(end));
        existingReservation.setRoomPreference(roomPreference);

        // Sauvegardez et retournez la réservation mise à jour
        return reservationRepository.save(existingReservation);
    }



}