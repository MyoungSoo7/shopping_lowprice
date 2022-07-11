package com.lms.springcore.service;

import com.lms.springcore.dto.ProductMypriceRequestDto;
import com.lms.springcore.dto.ProductRequestDto;
import com.lms.springcore.model.Product;
import com.lms.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
// 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);

        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        int myprice = requestDto.getMyprice();
        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products = productRepository.findAll();
        return products;
    }
}