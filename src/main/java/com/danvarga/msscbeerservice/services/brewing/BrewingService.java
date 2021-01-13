package com.danvarga.msscbeerservice.services.brewing;

import com.danvarga.msscbeerservice.config.JmsConfig;
import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.brewery.model.events.BrewBeerEvent;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import com.danvarga.msscbeerservice.services.inventory.BeerInventoryService;
import com.danvarga.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper mapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQuantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min On Hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQuantityOnHand);

            if (beer.getMinOnHand() >= invQuantityOnHand) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(mapper.beerToBeerDto(beer)));
            }
        });
    }
}
