package com.danvarga.common.events;

import com.danvarga.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -4394530510778788253L;

    private BeerDto beerDto;
}
