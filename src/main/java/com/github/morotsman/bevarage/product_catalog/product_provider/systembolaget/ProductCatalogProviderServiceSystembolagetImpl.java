package com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget;


import com.github.morotsman.bevarage.product_catalog.model.Product;
import com.github.morotsman.bevarage.product_catalog.product_provider.systembolaget.dto.ProductDto;
import com.github.morotsman.bevarage.product_catalog.service.ProductCatalogProviderService;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductCatalogProviderServiceSystembolagetImpl implements ProductCatalogProviderService{
    
    private final RestTemplate restTemplate;
    private final String providerUrl;
    
    @Autowired
    public ProductCatalogProviderServiceSystembolagetImpl(final RestTemplate restTemplate, 
            final @Value("${product_catalog.provider.systembolaget.url}") String providerUrl) {
        this.restTemplate = restTemplate;
        this.providerUrl = providerUrl;
    }
    
    private Product toProduct(final ProductDto from) {
        return new Product(from.getProductId(), from.getName1(), from.getProductCategory(), from.getPrice(), 
                from.getVolume(), from.getNr(), from.getArticelNumber(), from.getName2(), from.getPricePerLiter(), 
                from.getStartSellDate(), from.isExpired(), from.getType(), from.getStyle(), from.getPackaging(), 
                from.getSeal(), from.getOrigin(), from.getOriginCountry(), from.getProducer(), from.getSupplier(), 
                from.getVintage(), from.getAlcoholPercent(), from.getAssortment(), from.getAssortmentText(), 
                from.isEcologic(), from.isEtnic(), from.isKoscher(), from.getRawMaterialDescription());
    }
        
    
    //TODO we are using mock data for the time being, no need to hammer the service
    @Override
    public Stream<Product> getProductCatalog() {
        //final ProductCatalogeDto productCatalogeDto = restTemplate.getForObject(providerUrl, ProductCatalogeDto.class);
        //return productCatalogeDto.getProducts().stream().map(p -> toProduct(p));
        return Stream.of(new Product(1L, "A bear", "Öl", 12.0, 50.0, "2", "1243", "Another name", 24.0, new Date(), 
                false, "a type", "a style", "bottle", "a seal", "Malmoe", "Sweden", 
                "A nice comapany", "Some other company", "1972", "7.0%", "", "", 
                false, false, false, "some raw materials"));
    }  
    
}
