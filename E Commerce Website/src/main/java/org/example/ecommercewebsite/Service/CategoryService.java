package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Category;
import org.example.ecommercewebsite.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public Boolean updateCategory(Integer id,Category category){
        Category oldCategory=categoryRepository.getById(id);

        if (oldCategory==null)
            return false;

        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;
    }

    public Boolean deleteCategory(Integer id){
        Category category=categoryRepository.getById(id);

        if (category==null)
            return false;

        categoryRepository.delete(category);
        return true;
    }

    public boolean categoryExist(Integer id){
        List<Category> categories=getAllCategories();

        for (Category category: categories){
            if (category.getId().equals(id))
                return true;
        }
        return false;
    }

}
