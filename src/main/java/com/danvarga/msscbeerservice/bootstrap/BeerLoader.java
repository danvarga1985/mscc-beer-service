package com.danvarga.msscbeerservice.bootstrap;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/*
 Cannot set fix ids (always overwritten with random ones), and they need to match the ids in the
 beer-inventory-service... -> data.sql used instead.
 TODO: beer-inventory-service bootstrap has no problem setting ids - implement BaseEntity here to check if that helps.
*/

//@Component
// The CommandLineRunner interface ensures that the 'run' method will run every time the Spring context starts.
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234500037";
    public static final String BEER_3_UPC = "0631334260016";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeer();
    }

    private void loadBeer() {
        if (beerRepository.count() == 0) {

            beerRepository.save(Beer.builder()
                    .id(BEER_1_UUID)
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(BEER_2_UUID)
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("13.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(BEER_3_UUID)
                    .beerName("Duff")
                    .beerStyle("LAGER")
                    .quantityToBrew(200)
                    .minOnHand(13)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("19.95"))
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
