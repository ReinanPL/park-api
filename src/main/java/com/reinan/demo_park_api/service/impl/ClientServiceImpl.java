package com.reinan.demo_park_api.service.impl;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.exception.CpfUniqueViolationException;
import com.reinan.demo_park_api.exception.EntityNotFoundException;
import com.reinan.demo_park_api.repository.ClientRepository;
import com.reinan.demo_park_api.repository.projection.ClientProjection;
import com.reinan.demo_park_api.service.ClientService;
import com.reinan.demo_park_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;

    @Transactional
    @Override
    public Client saveClient(Client client) {
        try {
            client.setUser(getUserFromToken());
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("Client cpf '%s' already exists", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClientProjection> getAllClients(Pageable pageable){
        return clientRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Client getClientByUserId() {
        User  user = getUserFromToken();
        return clientRepository.findByUserId(user.getId());
    }

    public User getUserFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            Long id = Long.valueOf(authentication.getName());
            return userService.getUser(id);
        }
        return null;
    }

}
