package com.lms.springcore.service;

import com.lms.springcore.dto.ItemDto;
import com.lms.springcore.dto.ProductMypriceRequestDto;
import com.lms.springcore.dto.ProductRequestDto;
import com.lms.springcore.model.Folder;
import com.lms.springcore.model.Product;
import com.lms.springcore.model.Users;
import com.lms.springcore.repository.FolderRepository;
import com.lms.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@RequiredArgsConstructor   final 로 선언된 멤버 변수를 자동으로 생성합니다.
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    public static final int MIN_MY_PRICE = 100;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            FolderRepository folderRepository
    ) {
        this.productRepository = productRepository;
        this.folderRepository = folderRepository;
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId ) {
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
    }

    // (관리자용) 상품 전체 조회
    public Page<Product> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable);
    }

    // 상품 가격변경
    @Transactional
    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        product.setMyprice(myprice);
        productRepository.save(product);
        return product;
    }

    // 폴더에 상품 추가
    @Transactional
    public Product addFolder(Long productId, Long folderId, Users user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NullPointerException("해당 상품 아이디가 존재하지 않습니다."));
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NullPointerException("해당 폴더 아이디가 존재하지 않습니다."));
        Long loginUserId = user.getId();
        // 시스템 문제로 인해 폴더에 상품을 추가할 때, 회원과 상품 혹은 폴더 매칭이 잘못되었을 경우
        if (!product.getUserId().equals(loginUserId) || !folder.getUser().getId().equals(loginUserId)) {
            throw new IllegalArgumentException("회원님과 상품 혹은 폴더 매칭이 잘못되었습니다.");
        }

        product.addFolder(folder);
        return product;
    }

    // 스케줄러로 주기적으로 호출될 메소드
    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        int lowPrice = itemDto.getLprice();
        product.setMyprice(lowPrice);
        productRepository.save(product);
        return id;
    }

}