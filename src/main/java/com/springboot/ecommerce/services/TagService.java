package com.springboot.ecommerce.services;


import com.springboot.ecommerce.entities.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    void saveProductTag(Tag tag);

    List<Tag> getAllProductTags();

    void deleteTag(String id);

    Tag getTagById(String id);

    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Tag> getAllTagWithPaginationAndSort(int pageNo, int pageSize, String sortField, String sortDirection);

}
