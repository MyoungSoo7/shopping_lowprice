package com.lms.springcore.model;

import com.lms.springcore.dto.ProductRequestDto;
import com.lms.springcore.validator.ProductValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Product extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    private List<Folder> folderList;

    public Product(ProductRequestDto requestDto, Long userId) {
        ProductValidator.validateProductInput(requestDto, userId);

        this.userId = userId;
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", lprice=" + lprice +
                ", myprice=" + myprice +
                ", userId=" + userId +
                ", folderList=" + folderList +
                '}';
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}