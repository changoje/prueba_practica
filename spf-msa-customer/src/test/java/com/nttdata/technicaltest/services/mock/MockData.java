package com.nttdata.technicaltest.services.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.technicaltest.services.domain.dto.*;
import com.nttdata.technicaltest.services.model.GetCustomerSearchResponse;
import com.nttdata.technicaltest.services.model.PutCustomerRequest;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.model.PostCustomerCreateRequest;

import java.math.BigDecimal;

public class MockData {
    public static PostCustomerDto buildPostCustomerCreateRequest() throws JsonProcessingException {
        String object = "{\"userName\":\"jchango\",\"documentNumber\":\"12345678\",\"name\":\"Juan Pérez\",\"password\":\"securePassword123\",\"address\":\"Av. Siempre Viva 742\",\"phone\":\"555-0123\",\"genderTypeEnum\":\"MALE\",\"age\":\"30\",\"accountTypeEnum\":\"AHORRO\",\"initialBalance\":1000.5}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PostCustomerDto.class);
    }

    public static PostCustomerCreateRequest buildPostCustomerCreateRequestI() throws JsonProcessingException {
        String object = "{\"userName\":\"jchango\",\"documentNumber\":\"1716456764\",\"name\":\"Kael Quilaba\",\"password\":\"234324567f8\",\"address\":\"La manzana\",\"phone\":\"0985632469\",\"gender\":\"MALE\",\"age\":23,\"accountType\":\"AHORRO\",\"initialBalance\":560}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PostCustomerCreateRequest.class);
    }

    public static PostCustomerCreateRequest buildPostCustomerCreateRequestIe() throws JsonProcessingException {
        String object = "{\"userName\":\"jchango\",\"documentNumber\":\"0201059975\",\"name\":\"Lucas Santos\",\"password\":\"234324567f8\",\"address\":\"La gran manzana\",\"phone\":\"0985632469\",\"gender\":\"MALE\",\"age\":25,\"accountType\":\"CORRIENTE\",\"initialBalance\":800}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PostCustomerCreateRequest.class);
    }

    public static PutCustomerDto buildPutCustomerUpdateRequest() throws JsonProcessingException {
        String object = "{\"name\":\"Jimmy Chango\",\"password\":\"234324567f8\",\"address\":\"La manzana 12\",\"phone\":\"0996397688\",\"genderTypeEnum\":\"MALE\",\"age\":23,\"status\":{\"code\":\"DISABLED\"}}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PutCustomerDto.class);
    }

    public static PutCustomerRequest buildPutCustomerUpdateReq() throws JsonProcessingException {
        String object = "{\"name\":\"Jimmy Chango\",\"password\":\"234324567f8\",\"address\":\"La manzana 12\",\"phone\":\"0996397688\",\"gender\":\"MALE\",\"age\":23,\"status\":{\"code\":\"DISABLED\"}}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PutCustomerRequest.class);
    }

    public static GetCustomerSearchResponse buildGetCustomerSearchResponse() throws JsonProcessingException {
        String object = "{\"documentNumber\":\"0678504644\",\"name\":\"Kael Quilaba\",\"address\":\"La manzana\",\"phone\":\"0985632469\",\"gender\":\"MALE\",\"age\":23,\"status\":{\"code\":\"ENABLED\"}}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, GetCustomerSearchResponse.class);
    }

    public static PersonEntityDto personDto() {
        PersonEntityDto personEntityDto = new PersonEntityDto();
        personEntityDto.setPersonId(12L);
        personEntityDto.setDocumentNumber("1716456764");
        personEntityDto.setName("Kael Quilaba");
        personEntityDto.setAge("23");
        personEntityDto.setAddress("La manzana");
        personEntityDto.setGender("MALE");
        personEntityDto.setPhone("0985632469");
        return personEntityDto;
    }

    public static PersonEntity entityPerson() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setPersonId(12L);
        personEntity.setDocumentNumber("1716456764");
        personEntity.setName("Kael Quilaba");
        personEntity.setAge("23");
        personEntity.setAddress("La manzana");
        personEntity.setGender("MALE");
        personEntity.setPhone("0985632469");
        return personEntity;
    }

    public static CustomerEntityDto customerDto() {
        CustomerEntityDto customerDto = new CustomerEntityDto();
        customerDto.setCustomerId(12L);
        customerDto.setPassword("545469");
        customerDto.setStatus("ENABLED");
        customerDto.setPersonId(12L);
        return customerDto;
    }

    public static CustomerCacheDto customerCacheDto() {
        return CustomerCacheDto
                .builder()
                .customerId(12L)
                .accountType("4521562456")
                .status("ENABLED")
                .documentNumber("2365897896")
                .name("Jose Ruiz")
                .initialBalance(BigDecimal.valueOf(526))
                .password("545469")
                .build();
    }
}
