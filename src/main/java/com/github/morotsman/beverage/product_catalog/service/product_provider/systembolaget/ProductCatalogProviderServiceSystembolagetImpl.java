package com.github.morotsman.beverage.product_catalog.service.product_provider.systembolaget;


import com.github.morotsman.beverage.model.product.Product;
import com.github.morotsman.beverage.product_catalog.service.product_provider.systembolaget.dto.ProductDto;
import com.github.morotsman.beverage.product_catalog.service.ProductCatalogProviderService;
import com.github.morotsman.beverage.product_catalog.service.product_provider.systembolaget.dto.ProductCatalogeDto;
import java.util.Date;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductCatalogProviderServiceSystembolagetImpl implements ProductCatalogProviderService{
    
    private final RestTemplate restTemplate;
    private final String providerUrl;
    private boolean useFakeData = false;
    
    @Autowired
    public ProductCatalogProviderServiceSystembolagetImpl(final RestTemplate restTemplate, 
            final @Value("${product_catalog.provider.systembolaget.url}") String providerUrl,
            final @Value("${product_catalog.provider.systembolaget.use_fake_data}") Boolean useFakeData) {
        this.restTemplate = restTemplate;
        this.providerUrl = providerUrl;
        this.useFakeData = useFakeData;
    }
    
    private String getName(final ProductDto from) {
        if (from.getName2() == null || from.getName2().length() == 0) return from.getName1();
        return from.getName1() + " (" + from.getName2() + ")";
    }
    
    private Product toProduct(final ProductDto from) {
        return new Product(from.getProductId(), getName(from), from.getProductCategory(), from.getPrice(), 
                from.getVolume(), from.getNr(), from.getArticelNumber(), from.getPricePerLiter(), 
                from.getStartSellDate(), from.isExpired(), from.getType(), from.getStyle(), from.getPackaging(), 
                from.getSeal(), from.getOrigin(), from.getOriginCountry(), from.getProducer(), from.getSupplier(), 
                from.getVintage(), from.getAlcoholPercent(), from.getAssortment(), from.getAssortmentText(), 
                from.isEcologic(), from.isEtnic(), from.isKoscher(), from.getRawMaterialDescription(),null,0);
    }
        
    
    private Stream<Product> getProducts(final int numberOfProducts) {
        return LongStream.range(0, numberOfProducts).mapToObj(i -> {
            return new Product(i, "A bear " + i, "Bear", 12.0, 50.0, "2", "1243", 24.0, new Date(), 
                false, "a type", "a style", "bottle", "a seal", "Malmoe", "Sweden", 
                "A nice comapany " + (i+1), "Some other company", "1972", "7.0%", "", "", 
                false, false, false, "some raw materials",8.1,20);
        });
    }  
     
    //TODO we are using mock data for the time being, no need to hammer the service
    @Override
    public Stream<Product> getProductCatalog() {  
        if(!useFakeData) {
            final ProductCatalogeDto productCatalogeDto = restTemplate.getForObject(providerUrl, ProductCatalogeDto.class);
            return productCatalogeDto.getProducts().stream().map(p -> toProduct(p)); 
        } else {
            return getProducts(500);  
        }  
    }    
    
}
