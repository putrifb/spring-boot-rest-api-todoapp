package restapi.todoapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.todoapp.dto.request.CategoryRequest;
//import restapi.todoapp.dto.request.UpdateCategoryRequest;
import restapi.todoapp.dto.response.CategoryResponse;
import restapi.todoapp.dto.response.CommonResponse;
import restapi.todoapp.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CommonResponse> insertCategory(@RequestBody CategoryRequest categoryRequest){
       return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.insertCategory(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping( path = "/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
//
    @PutMapping("/{categoryId}")
    public ResponseEntity<CommonResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateCategory(categoryId,request));
    }
//
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponse> deleteCategory(@PathVariable Long categoryId) {
       categoryService.deleteCategory(categoryId);
       return ResponseEntity.ok(new CommonResponse());
    }


}
