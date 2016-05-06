package com.webapplication.service.category;

import com.webapplication.dao.CategoryRepository;
import com.webapplication.dto.category.CategoryResponseDto;
import com.webapplication.entity.Category;
import com.webapplication.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class CategoryServiceApiImpl implements CategoryServiceApi {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.categoryListToCategoryResponseDto(categories);
    }

}
