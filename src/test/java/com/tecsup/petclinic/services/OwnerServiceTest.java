package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testCreateOwner() {

        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Ramirez";
        String ADDRESS = "Av. Los Alamos 123";
        String CITY = "Arequipa";
        String TELEPHONE = "987654321";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

        Owner newOwner = ownerService.create(owner);
        log.info("Owner creado: " + newOwner);

        assertNotNull(newOwner.getId());
        assertEquals(FIRST_NAME, newOwner.getFirstName());
        assertEquals(CITY, newOwner.getCity());
    }

    @Test
    public void testFindOwnerById() {

        long ID = 1L;
        String FIRST_NAME = "George";
        Owner owner = null;

        try {
            owner = ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail("" + e.getMessage());
        }

        log.info("Owner encontrado: " + owner);
        assertEquals(FIRST_NAME, owner.getFirstName());
    }


    @Test
    public void testUpdateOwner() {

        String FIRST_NAME = "Lucía";
        String LAST_NAME = "Gomez";
        String ADDRESS = "Av. Lima 321";
        String CITY = "Lima";
        String TELEPHONE = "912345678";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
        Owner newOwner = ownerService.create(owner);

        log.info("Owner antes de actualizar: " + newOwner);

        String NEW_CITY = "Cusco";
        newOwner.setCity(NEW_CITY);

        Owner updatedOwner = ownerService.update(newOwner);
        log.info("Owner actualizado: " + updatedOwner);

        assertEquals(NEW_CITY, updatedOwner.getCity());
    }

    @Test
    public void testDeleteOwner() {

        String FIRST_NAME = "María";
        String LAST_NAME = "Perez";
        String ADDRESS = "Av. Salaverry 999";
        String CITY = "Piura";
        String TELEPHONE = "976543210";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
        Owner newOwner = ownerService.create(owner);

        log.info("Owner creado para eliminar: " + newOwner);

        long ID = newOwner.getId();

        try {
            ownerService.delete(ID);
        } catch (OwnerNotFoundException e) {
            fail("" + e.getMessage());
        }

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findById(ID);
        });

        log.info("Owner eliminado correctamente con ID = " + ID);
    }
}
