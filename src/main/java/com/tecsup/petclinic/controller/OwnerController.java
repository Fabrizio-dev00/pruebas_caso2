package com.tecsup.petclinic.controller;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable long id) {
        try {
            Owner owner = ownerService.findById(id);
            return ResponseEntity.ok(owner);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner newOwner = ownerService.create(owner);
        return ResponseEntity
                .created(null)
                .body(newOwner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable long id, @RequestBody Owner owner) {
        try {
            Owner existingOwner = ownerService.findById(id);

            existingOwner.setFirstName(owner.getFirstName());
            existingOwner.setLastName(owner.getLastName());
            existingOwner.setAddress(owner.getAddress());
            existingOwner.setCity(owner.getCity());
            existingOwner.setTelephone(owner.getTelephone());

            Owner updated = ownerService.update(existingOwner);
            return ResponseEntity.ok(updated);

        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable long id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}