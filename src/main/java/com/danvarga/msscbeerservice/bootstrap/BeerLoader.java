package com.danvarga.msscbeerservice.bootstrap;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// The CommandLineRunner interface ensures that the 'run' method will run every time the Spring context starts.
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234500037";
    public static final String BEER_3_UPC = "0631334260016";

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
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("13.95"))
                    .build());

            beerRepository.save(Beer.builder()
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
