package com.danvarga.msscbeerservice.bootstrap;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import com.danvarga.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
 TODO: beer-inventory-service bootstrap has no problem setting ids - implement BaseEntity here to check if that helps.
*/

@Component
@RequiredArgsConstructor
// The CommandLineRunner interface ensures that the 'run' method will run every time the Spring context starts.
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234500037";
    public static final String BEER_3_UPC = "0631334260016";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeer();
    }

    private void loadBeer() {
        if (beerRepository.count() == 0) {

            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA.name())
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE.name())
                    .quantityToBrew(200)
                    .minOnHand(10)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("13.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Pinball Porter")
                    .beerStyle(BeerStyleEnum.PORTER.name())
                    .quantityToBrew(200)
                    .minOnHand(13)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("19.95"))
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
