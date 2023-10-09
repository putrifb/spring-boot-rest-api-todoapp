package restapi.todoapp.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restapi.todoapp.dto.request.CategoryRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.CommonResponse;
import restapi.todoapp.entity.Category;
import restapi.todoapp.exception.ResourceNotFoundException;
import restapi.todoapp.repository.CategoryRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;



    public CommonResponse insertCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setTitle(categoryRequest.getTitle());

        ZoneId zoneId =ZoneId.of("Asia/Jakarta");

        ZonedDateTime createdAt = ZonedDateTime.now(zoneId);
        category.setCreatedAt(createdAt);

        category.setIsDeleted(false);
        Category savedCategory = categoryRepository.save(category);
//        CategoryResponse response = new CategoryResponse();
//        response.setId(savedCategory.getId());

        return new CommonResponse(savedCategory.getId());
    }

    public List<CategoryResponse> getAllCategory(){
        List<Category> categories = categoryRepository.findByIsDeletedFalse();

        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setTitle(category.getTitle());


            categoryResponses.add(categoryResponse);
        }


        return categoryResponses;
    }

    public CategoryResponse getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(categoryId);

//        if (optionalCategory.isPresent()) {
//            Category category = optionalCategory.get();
//            CategoryResponse categoryResponse = new CategoryResponse();
//            categoryResponse.setId(category.getId());
//            categoryResponse.setTitle(category.getIsDeleted() ? "" : category.getTitle());
//            return categoryResponse;
//        } else {
////            return null;
//            throw new ResourceNotFoundException("category not found");
//        }

        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("category not found");
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(optionalCategory.get().getId());
        categoryResponse.setTitle(optionalCategory.get().getTitle());

        return categoryResponse;
    }

   public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {

            Category category = optionalCategory.get();
            category.setTitle(request.getTitle());

            ZoneId zoneId =ZoneId.of("Asia/Jakarta");
            ZonedDateTime updateAt = ZonedDateTime.now(zoneId);
            category.setUpdatedAt(updateAt);

            Category updateCategory = categoryRepository.save(category);
            CategoryResponse categoryResponse = new CategoryResponse();

            categoryResponse.setId(updateCategory.getId());
            categoryResponse.setTitle(updateCategory.getTitle());
            return categoryResponse;
        } else {
            return null;
        }
    }

    public Boolean deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        } else {
            return false;
        }

//        if (optionalCategory.isPresent()){
//            return false;
//        }
//        categoryRepository.deleteById(categoryId);
//        return true;
    }


}
