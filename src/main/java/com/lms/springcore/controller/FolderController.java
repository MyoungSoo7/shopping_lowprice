package com.lms.springcore.controller;

import com.lms.springcore.dto.FolderRequestDto;
import com.lms.springcore.exception.RestApiException;
import com.lms.springcore.model.Folder;
import com.lms.springcore.model.Users;
import com.lms.springcore.security.UserDetailsImpl;
import com.lms.springcore.service.FolderService;
import com.lms.springcore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




@RestController
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("api/folders")
    public List<Folder> addFolders(
            @RequestBody FolderRequestDto folderRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<String> folderNames = folderRequestDto.getFolderNames();
        Users user = userDetails.getUser();

        return folderService.addFolders(folderNames, user);
    }

    // 회원이 등록한 모든 폴더 조회
    @GetMapping("api/folders")
    public List<Folder> getFolders(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return folderService.getFolders(userDetails.getUser());
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("api/folders/{folderId}/products")
    public Page<Product> getProductsInFolder(
            @PathVariable Long folderId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        page = page - 1;
        return folderService.getProductsInFolder(
                folderId,
                page,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());
        return new ResponseEntity(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}