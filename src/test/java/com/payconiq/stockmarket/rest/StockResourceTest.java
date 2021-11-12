package com.payconiq.stockmarket.rest;

import com.payconiq.stockmarket.Application;
import com.payconiq.stockmarket.dto.JwtTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author : M.GolMohammadi
 * @mailto : M.GolMohammadi@emofid.com
 * @created : 11/17/2021, Wednesday
 **/


@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockResourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockResourceTest.class);

    //   @Test
    void requestToGetTokenAdminRole_login_ReturnToken() {
        String response = getToken();
        assertNotNull(response);
    }


    //    @Test
    void findAll_getAllStocks_shouldReturnAllResources() {

        TestRestTemplate restTemplate = new TestRestTemplate();
        String theUrl = "http://localhost:8080/api/stocks";

        String token = getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "bearer".concat(" ").concat(token));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<List> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, List.class);

        assertEquals(response.getStatusCodeValue(), 200);
    }

    String getToken() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url = "http://localhost:8080/users/signin";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", "admin");
        params.add("password", "123");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        LOGGER.info("Request to get Token: {}", request);
        ResponseEntity<JwtTokenDTO> responseEntity = restTemplate.postForEntity(url, request, JwtTokenDTO.class);
        return Objects.requireNonNull(responseEntity.getBody()).getToken();


    }

}