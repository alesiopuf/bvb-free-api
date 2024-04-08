package com.dp.bvb.converter;

import com.dp.bvb.TestUtil;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.entity.TradeOrder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TradeOrderConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ApplicationConverter applicationConverter;

    public TradeOrderConverterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEntityToDto() {
        TradeOrder order = TestUtil.getTradeOrder1();
        TradeOrderDTO expectedDto = TestUtil.getTradeOrderDTO1();
        when(modelMapper.map(order, TradeOrderDTO.class)).thenReturn(expectedDto);

        TradeOrderDTO actualDto = applicationConverter.entityToDto(order);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testDtoToEntity() {
        TradeOrderDTO dto = TestUtil.getTradeOrderDTO1();
        TradeOrder expectedOrder = TestUtil.getTradeOrder1();
        when(modelMapper.map(dto, TradeOrder.class)).thenReturn(expectedOrder);

        TradeOrder actualOrder = applicationConverter.dtoToEntity(dto);

        assertEquals(expectedOrder, actualOrder);
    }
}
