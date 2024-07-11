package com.lms.springcore.utils;

import com.lms.springcore.dto.ItemDto;
import com.lms.springcore.model.Product;
import com.lms.springcore.repository.ProductRepository;
import com.lms.springcore.service.ItemSearchService;
import com.lms.springcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ItemSearchService itemSearchService;

    // 초(0~59), 분(0-59), 시(0~23), 일(1-31), 월(1-12), 요일(0~6), 연도(생략가능) 순서
    @Scheduled(cron = "0 0 1 * * * ?")
    public void updatePrice() throws InterruptedException, IOException {
        log.info("가격 업데이트 실행");
        List<Product> productList = productRepository.findAll();
        for (int i = 0; i < productList.size(); i++) {
            TimeUnit.SECONDS.sleep(1);
            Product p = productList.get(i);
            String title = p.getTitle();

            // 네이버쇼핑 API 에서 관심 상품의 검색
            List<ItemDto> itemDtoList = itemSearchService.getItems(title);
            if(itemDtoList.size() != 0){
                ItemDto itemDto = itemDtoList.get(0);
                // 관심 상품 정보 가격 업데이트합니다.
                Long id = p.getId();
                productService.updateBySearch(id, itemDto);
            }
        }
    }
}

