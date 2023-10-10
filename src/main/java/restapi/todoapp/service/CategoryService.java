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
import restapi.todoapp.repository.TodoRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;



    public CommonResponse insertCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setTitle(categoryRequest.getTitle());

        ZoneId zoneId =ZoneId.of("Asia/Jakarta");

        ZonedDateTime createdAt = ZonedDateTime.now(zoneId);
        category.setCreatedAt(createdAt);

        category.setIsDeleted(false);
        Category savedCategory = categoryRepository.save(category);


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

        if (optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("category not found");
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(optionalCategory.get().getId());
        categoryResponse.setTitle(optionalCategory.get().getTitle());
        return categoryResponse;
    }

   public CommonResponse updateCategory(Long categoryId, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(categoryId);

       if (optionalCategory.isEmpty()){
           throw new ResourceNotFoundException("category not found");
       }

       optionalCategory.get().setTitle(request.getTitle());

       ZoneId zoneId =ZoneId.of("Asia/Jakarta");
       ZonedDateTime updateAt = ZonedDateTime.now(zoneId);
       optionalCategory.get().setUpdatedAt(updateAt);

       Category updateCategory = categoryRepository.save(optionalCategory.get());

       return new CommonResponse(updateCategory.getId());
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(categoryId);

        if (optionalCategory.isEmpty()){
            throw new ResourceNotFoundException("Category not found with id : " + categoryId);
        }

        Long todoCount = todoRepository.countByIsDeletedTrueAndCategory_Id(categoryId);

        if (todoCount > 0 ){
            throw new ResourceNotFoundException("Category is in use by existing todos and cannot be deleted.");
        }

        categoryRepository.deleteById(categoryId);
    }


}
