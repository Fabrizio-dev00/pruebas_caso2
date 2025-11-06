package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner findById(long id) throws OwnerNotFoundException {
        Optional<Owner> optional = ownerRepository.findById(id);
        if (optional.isEmpty()) {
            throw new OwnerNotFoundException("Owner con ID " + id + " no encontrado");
        }
        return optional.get();
    }

    public Owner update(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void delete(long id) throws OwnerNotFoundException {
        if (!ownerRepository.existsById(id)) {
            throw new OwnerNotFoundException("Owner con ID " + id + " no existe para eliminar");
        }
        ownerRepository.deleteById(id);
    }
}