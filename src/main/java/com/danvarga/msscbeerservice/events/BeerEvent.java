package com.danvarga.msscbeerservice.events;

import com.danvarga.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -4394530510778788253L;

    private final BeerDto beerDto;
}
