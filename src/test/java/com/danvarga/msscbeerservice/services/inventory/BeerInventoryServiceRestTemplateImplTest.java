package com.danvarga.msscbeerservice.services.inventory;

import com.danvarga.msscbeerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled // Utility for manual Testing.
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Test
    void getOnHandInventory() {
        Integer quantityOnHand = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);

        System.out.println(quantityOnHand);
    }

}