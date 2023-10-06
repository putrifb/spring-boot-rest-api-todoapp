package restapi.todoapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.todoapp.dto.request.CategoryRequest;
//import restapi.todoapp.dto.request.UpdateCategoryRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/categories")
    public void insertCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.insertCategory(categoryRequest);
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping( path = "/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
//
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Map<String, String> request) {
        String newTitle = request.get("title");

            CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, newTitle);
            return ResponseEntity.ok(categoryResponse);

//        return ResponseEntity.ok(categoryService.updateCategory(categoryId, request.getTitle()));
    }
//
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId) {
        Boolean isDeleted = categoryService.deleteCategory(categoryId);
        if (isDeleted){
            return ResponseEntity.ok().build();
        } else {
            return null;
        }

    }


}
