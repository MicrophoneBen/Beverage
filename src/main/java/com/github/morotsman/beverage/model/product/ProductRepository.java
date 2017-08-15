package com.github.morotsman.beverage.model.product;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    
    List<Product> findByName(final String query);
    
    List<Product> findByProductCategory(final String productCategory);
    
    List<Product> findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(final String name, final String producer);
    
    List<Product> findDistinctProductsByNameIgnoreCaseContainingOrProducerIgnoreCaseContaining(final String name, final String producer,final Pageable pageable);
    
    Optional<Product> findOne(long productId);  
    
    final static String updateProductStatement = "update Product p set "
            + "p.name = ?1, p.productCategory = ?2, p.price = ?3, "
            + "p.volume = ?4, p.nr = ?5, p.articelNumber = ?6, "
            + "p.pricePerLiter = ?7, p.startSellDate = ?8, p.expired = ?9, "
            + "p.type = ?10, p.style = ?11, p.packaging = ?12, "
            + "p.seal = ?13, p.origin = ?14, p.originCountry = ?15, "
            + "p.producer = ?16, p.supplier = ?17, p.vintage = ?18, "
            + "p.alcoholPercent = ?19, p.assortment = ?20, p.assortmentText = ?21, "
            + "p.ecologic = ?22, p.etnic = ?23, p.koscher = ?24, "
            + "p.rawMaterialDescription = ?25 "
            + "where p.id = ?26";  
    
    @Modifying  
    @Query(updateProductStatement)   
    int updateProduct(String name, String productCategory, Double price, Double volume, String nr, String articelNumber, 
            Double pricePerLiter, Date startSellDate, Boolean expired, String type, String style, String packaging, String seal, 
            String origin, String originCountry, String producer, String supplier, String vintage, String alcoholPercent, 
            String assortment, String assortmentText, Boolean ecologic, Boolean etnic, Boolean koscher, String rawMaterialDescription, Long id);


    final static String updateProductAverageRateAndNumberOfReviews = "update Product p set "
            + "p.averageRate = ?1, p.numberOfReviews = ?2 "
            + "where p.id = ?3";  

    @Modifying  
    @Query(updateProductAverageRateAndNumberOfReviews)   
    int updateProductAverageRateAndNumberOfReviews(Double averageRate, Long numberOfReviews, Long id);


    
}
