package com.danvarga.msscbeerservice.services;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.repositories.BeerRepository;
import com.danvarga.msscbeerservice.web.controller.NotFoundException;
import com.danvarga.msscbeerservice.web.mappers.BeerMapper;
import com.danvarga.msscbeerservice.web.model.BeerDto;
import com.danvarga.msscbeerservice.web.model.BeerPagedList;
import com.danvarga.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        );
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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

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

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPagedList;
    }
}

