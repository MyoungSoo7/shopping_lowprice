package com.lms.springcore.service;

import com.lms.springcore.model.Folder;
import com.lms.springcore.model.Product;
import com.lms.springcore.model.Users;
import com.lms.springcore.repository.FolderRepository;
import com.lms.springcore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FolderService(
            FolderRepository folderRepository,
            ProductRepository productRepository
    ) {
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public List<Folder> addFolders(List<String> folderNames, Users user) {
        List<Folder> savedFolderList = new ArrayList<>();
        for (String folderName : folderNames) {
            Folder folder = createFolderOrThrow(folderName, user);
            savedFolderList.add(folder);
        }

        return savedFolderList;
    }

    public Folder createFolderOrThrow(String folderName, Users user) {
        boolean isExistFolder = folderRepository.existsByUserAndName(user, folderName);
        if (isExistFolder) {
            throw new IllegalArgumentException("중복된 폴더("+ folderName + ")가 있습니다!");
        }
        Folder folder = new Folder(folderName, user);
        return folderRepository.save(folder);
    }

    public List<Folder> getFolders(Users user) {
        return folderRepository.findAllByUser(user);
    }

    // 폴더에 속한 상품들을 조회하는 메소드
    public Page<Product> getProductsInFolder(
            Long folderId,
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            Users user
    ) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Long userId = user.getId();
        return productRepository.findAllByUserIdAndFolderList_Id(userId, folderId, pageable);
    }
}