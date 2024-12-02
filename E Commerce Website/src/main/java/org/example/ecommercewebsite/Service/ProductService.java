package org.example.ecommercewebsite.Service;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Boolean addProduct(Product product){
        if (!categoryService.categoryExist(product.getCategoryId()))
            return false;

        productRepository.save(product);
        return true;
    }

    public int updateProduct(Integer id,Product product){
        if (!categoryService.categoryExist(product.getCategoryId()))
            return 0;

        Product oldProduct = productRepository.getById(id);

        if (oldProduct==null)
            return 1;

        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategoryId(product.getCategoryId());

        productRepository.save(oldProduct);
        return 2;
    }

    public Boolean deleteProduct(Integer id){
        Product product=productRepository.getById(id);

        if (product==null)
            return false;

        productRepository.delete(product);
        return true;
    }

    public boolean productExist(Integer id){
        List<Product> products=getAllProducts();

        for (Product product: products){
            if (product.getId().equals(id))
                return true;
        }
        return false;
    }

    public Product getProductById(Integer id){
        List<Product> products=getAllProducts();

        for (Product product: products){
            if (product.getId().equals(id))
                return product;
        }
        return null;
    }
}
