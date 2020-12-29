package com.danvarga.msscbeerservice.web.mappers;

import com.danvarga.msscbeerservice.domain.Beer;
import com.danvarga.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
