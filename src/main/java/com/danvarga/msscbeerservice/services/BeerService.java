package com.danvarga.msscbeerservice.services;

import com.danvarga.brewery.model.BeerDto;
import com.danvarga.brewery.model.BeerPagedList;
import com.danvarga.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BeerService {

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto getByUpc(String beerUpc, Boolean showInventoryOnHand);
}
