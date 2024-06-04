package com.br.sgme.adapters.in.controller.vendas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendaRequestDto {
    private VendaDto vendaDto;
    private List<ItemVendaDto> itensVendaDto;
}
