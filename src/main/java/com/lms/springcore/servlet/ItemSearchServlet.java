package com.lms.springcore.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.springcore.dto.ItemDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/search")
public class ItemSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 1. API Request 의 파라미터 값에서 검색어 추출 -> query 변수
        String query = request.getParameter("query");

// 2. 네이버 쇼핑 API 호출에 필요한 Header, Body 정리
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "zdqMoIkFaK8uKvC2oNY2");
        headers.add("X-Naver-Client-Secret", "LiZfsgtuD5");
        String body = "";
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

// 3. 네이버 쇼핑 API 호출 결과 -> naverApiResponseJson (JSON 형태)
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        String naverApiResponseJson = responseEntity.getBody();

// 4. naverApiResponseJson (JSON 형태) -> itemDtoList (자바 객체 형태)
// - naverApiResponseJson 에서 우리가 사용할 데이터만 추출 -> List<ItemDto> 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode itemsNode = objectMapper.readTree(naverApiResponseJson).get("items");
        List<ItemDto> itemDtoList = objectMapper
                .readerFor(new TypeReference<List<ItemDto>>() {})
                .readValue(itemsNode);

// 5. API Response 보내기
// 5.1) response 의 header 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
// 5.2) response 의 body 설정
        PrintWriter out = response.getWriter();
// - itemDtoList (자바 객체 형태) -> itemDtoListJson (JSON 형태)
        String itemDtoListJson = objectMapper.writeValueAsString(itemDtoList);
        out.print(itemDtoListJson);
        out.flush();
    }
}