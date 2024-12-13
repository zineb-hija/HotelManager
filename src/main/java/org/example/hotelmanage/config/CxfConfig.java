package org.example.hotelmanage.config;

import lombok.AllArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.example.hotelmanage.service.soap.ReservationSoapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class CxfConfig {
    @Autowired

    private ReservationSoapService reservationSoapService;
    @Autowired
    private Bus bus;
    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoapService);
        endpoint.publish("/ws");
        return endpoint;
    }
}