package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.tag.Tag;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.services.ProductService;
import com.springboot.ecommerce.services.TagService;
import com.springboot.ecommerce.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProductService productService;

    @Override
    public void saveProductTag(Tag tag) {
        tagRepository.save(tag);
    }


    @Override
    public List<Tag> getAllProductTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(String id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()){
            return optionalTag.get();
        } else {
            throw new IllegalStateException("Tag not found for id: " + id);
        }
    }

    @Override
    public void deleteTag(String id) {
        List<Product> products = productService.findAllByTag(id);
        Tag tag  = tagRepository.findById(id).orElse(null);
        for (Product product : products){
            product.getTags().remove(tag);
            productService.saveProduct(product);
        }
        tagRepository.deleteById(id);
    }

    @Override
    public Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    @Override
    public Page<Tag> getAllTagWithPaginationAndSort(int pageNo, int pageSize, String sortField, String sortDirection) {
        return tagRepository.findAll(
                this.findPaginated(pageNo, pageSize, sortField, sortDirection)
        );
    }
}
