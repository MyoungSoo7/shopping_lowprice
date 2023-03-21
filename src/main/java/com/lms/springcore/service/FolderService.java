package com.lms.springcore.service;

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

    // 로그인한 회원에 폴더들 등록
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
        // 입력으로 들어온 폴더 이름이 이미 존재하는 경우, Exception 발생
        boolean isExistFolder = folderRepository.existsByUserAndName(user, folderName);
        if (isExistFolder) {
            //throw ErrorCode.DUPLICATED_FOLDER_NAME;
            throw new IllegalArgumentException("중복된 폴더명을 제거해 주세요! 폴더명: " + folderName);
        }

        // 폴더명 저장
        Folder folder = new Folder(folderName, user);
        return folderRepository.save(folder);
    }

    // 로그인한 회원이 등록된 모든 폴더 조회
    public List<Folder> getFolders(Users user) {
        return folderRepository.findAllByUser(user);
    }

    // 회원 ID 가 소유한 폴더에 저장되어 있는 상품들 조회
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