package com.danvarga.msscbeerservice.web.mappers;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.services.inventory.BeerInventoryService;
import com.danvarga.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    // Autowired needed on setters, because constructor DI didn't work.
    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }


//    @Override
//    public BeerDto beerToBeerDto(Beer beer) {
//        return mapper.beerToBeerDto(beer);
//    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto beerDto = mapper.beerToBeerDto(beer);
        // Makes the whole app dependent on the 'beer-inventory-service', since every endpoint uses conversion.
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));

        return beerDto;
    }


    // TODO: Decide whether this is necessary to be overwritten (according to the documentation, probably not).
//    @Override
//    public Beer beerDtoToBeer(BeerDto dto) {
//        return mapper.beerDtoToBeer(dto);
//    }
}
