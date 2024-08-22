package com.reinan.demo_park_api.service;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    Client saveClient(Client client);
    Client getClientById(Long id);

    Page<ClientProjection> getAllClients(Pageable pageable);

    Client getClientByUserId();
}
