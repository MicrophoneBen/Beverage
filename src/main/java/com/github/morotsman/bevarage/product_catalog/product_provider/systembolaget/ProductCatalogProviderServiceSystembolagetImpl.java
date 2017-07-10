package com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget;


import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget.dto.ProductCatalogeDto;
import com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget.dto.ProductDto;
import com.github.morotsman.bevarage.product_catalog.service.ProductCatalogProviderService;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductCatalogProviderServiceSystembolagetImpl implements ProductCatalogProviderService{
    
    final RestTemplate restTemplate;
    
    public ProductCatalogProviderServiceSystembolagetImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    private Product toProduct(final ProductDto from) {
        return new Product(from.getProductId(),from.getName1(), from.getProductCategory(), from.getPrice());
    }
      
    public Stream<Product> getProductCatalog() {
        final ProductCatalogeDto productCatalogeDto = restTemplate.getForObject("https://www.systembolaget.se/api/assortment/products/xml", ProductCatalogeDto.class);
        System.out.println(productCatalogeDto.getProducts().get(1));
        return productCatalogeDto.getProducts().stream().map(p -> toProduct(p));   
    }  
    
}
