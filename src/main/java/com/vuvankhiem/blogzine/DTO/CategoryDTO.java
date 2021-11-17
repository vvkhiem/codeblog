package com.vuvankhiem.blogzine.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {
    String categoryName;
    long numberPosts;
}
