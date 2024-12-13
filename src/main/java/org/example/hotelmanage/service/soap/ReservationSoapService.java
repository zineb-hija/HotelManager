package org.example.hotelmanage.service.soap;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.example.hotelmanage.model.Reservation;
import org.example.hotelmanage.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@WebService(serviceName = "HotelWs")
public class ReservationSoapService {
    @Autowired
    private ReservationRepository reservationRepository;

    @WebMethod
    public List<Reservation> getReservation() {
        return reservationRepository.findAll();
    }

    @WebMethod
    public Reservation getReservationById(@WebParam(name = "id")Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @WebMethod
    public Reservation createReservation(@WebParam(name = "clientName") String clientName,
                                         @WebParam(name = "checkInDate")String checkInDate,
                                         @WebParam(name = "checkOutDate")String checkOutDate,
                                         @WebParam(name = "roomPreference")String roomPreference) {
       Reservation reservation = new Reservation(null,clientName,roomPreference,checkInDate,checkOutDate);
       return reservationRepository.save(reservation);
    }

    @WebMethod
    public boolean deleteReservation(@WebParam(name = "id")Long id) {
      if (reservationRepository.existsById(id)) {
          reservationRepository.deleteById(id);
          return true;
      }
      return false;
    }
}
