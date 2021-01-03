package com.danvarga.msscbeerservice.services;

import com.danvarga.msscbeerservice.web.model.BeerDto;
import com.danvarga.msscbeerservice.web.model.BeerPagedList;
import com.danvarga.msscbeerservice.web.model.BeerStyleEnum;
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
