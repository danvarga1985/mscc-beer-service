package com.danvarga.msscbeerservice.services;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import com.danvarga.msscbeerservice.web.controller.NotFoundException;
import com.danvarga.msscbeerservice.web.mappers.BeerMapper;
import com.danvarga.brewery.model.BeerDto;
import com.danvarga.brewery.model.BeerPagedList;
import com.danvarga.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @SneakyThrows
    @Override
    // Conditional caching - only if Inventory information is not needed, as it tends to change frequently.
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {

        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        } else {
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }
    }

    @SneakyThrows
    @Override
    @Cacheable(cacheNames = "beerUpcCache", key = "#beerUpc", condition = "#showInventoryOnHand == false ")
    public BeerDto getByUpc(String beerUpc, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            try {
                return beerMapper.beerToBeerDtoWithInventory(
                        beerRepository.findByUpc(beerUpc));
            } catch (NullPointerException ex) {
                throw new NotFoundException();
            }
        } else {
            Beer foundBeer = beerRepository.findByUpc(beerUpc);
            if (foundBeer == null) {
                throw new NotFoundException();
            } else {
                return beerMapper.beerToBeerDto(foundBeer);
            }
        }
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @SneakyThrows
    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beer.getBeerStyle());
        beer.setUpc(beerDto.getUpc());
        beer.setPrice(beerDto.getPrice());

        Beer savedBeer = beerRepository.save(beer);

        return beerMapper.beerToBeerDto(savedBeer);
    }

    // Conditional caching - only if Inventory information is not needed, as it tends to change frequently.
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
                                   Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }
}

