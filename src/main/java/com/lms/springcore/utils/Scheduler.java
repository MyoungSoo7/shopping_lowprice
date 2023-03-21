package com.lms.springcore.utils;

import com.lms.springcore.dto.ItemDto;
import com.lms.springcore.model.Product;
import com.lms.springcore.repository.ProductRepository;
import com.lms.springcore.service.ItemSearchService;
import com.lms.springcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class Scheduler {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ItemSearchService itemSearchService;

    // 초(0~59), 분(0-59), 시(0~23), 일(1-31), 월(1-12), 요일(0~6), 연도(생략가능) 순서
    @Scheduled(cron = "0 0/1 * * * ?")
    public void auto1Update(){
        System.out.println("1분마다 실행됩니다.");
    }

    // 초(0~59), 분(0-59), 시(0~23), 일(1-31), 월(1-12), 요일(0~6), 연도(생략가능) 순서
    @Scheduled(cron = "0 0/2 * * * ?")
    public void updatePrice() throws InterruptedException, IOException {
        System.out.println("가격 업데이트 실행");
        // 저장된 모든 관심상품을 조회합니다.
        List<Product> productList = productRepository.findAll();

        System.out.println("productList=>"+productList.toString());
        for (int i = 0; i < productList.size(); i++) {
            // 1초에 한 상품 씩 조회합니다 (Naver 제한)
            TimeUnit.SECONDS.sleep(1);
            // i 번째 관심 상품을 꺼냅니다.
            Product p = productList.get(i);
            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
            System.out.println("product=>"+p.toString());
            String title = p.getTitle();
            //String resultString = itemSearchService.search(title);
            // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
            //List<ItemDto> itemDtoList = itemSearchService.fromJSONtoItems(resultString);
            List<ItemDto> itemDtoList = itemSearchService.getItems(title);
            ItemDto itemDto = itemDtoList.get(0);
            System.out.println("itemDtoList=>"+itemDtoList);
            System.out.println("itemDto=>"+itemDto);
            // i 번째 관심 상품 정보를 업데이트합니다.
            Long id = p.getId();
            productService.updateBySearch(id, itemDto);
        }
    }
}

