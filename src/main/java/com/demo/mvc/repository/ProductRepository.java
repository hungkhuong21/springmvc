package com.demo.mvc.repository;

import com.demo.mvc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = """
        SELECT 
            p.product_id,
            pt.product_name,
            pct.category_name,
            p.price,
            p.weight
        FROM products p
        LEFT JOIN product_translation pt
            ON p.product_id = pt.product_id AND pt.language_id = :lang
        LEFT JOIN product_category pc
            ON p.product_category_id = pc.product_category_id
        LEFT JOIN product_category_translation pct
            ON pc.product_category_id = pct.product_category_id AND pct.language_id = :lang
        """, nativeQuery = true)


    List<Object[]> findProductsByLanguage(@Param("lang") String lang);

    @Query(value = """
    SELECT 
        p.product_id,
        pt.product_name,
        pct.category_name,
        p.price,
        p.weight
    FROM products p
    LEFT JOIN product_translation pt
        ON p.product_id = pt.product_id AND pt.language_id = :lang
    LEFT JOIN product_category pc
        ON p.product_category_id = pc.product_category_id
    LEFT JOIN product_category_translation pct
        ON pc.product_category_id = pct.product_category_id AND pct.language_id = :lang
    WHERE LOWER(pt.product_name) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(pct.category_name) LIKE LOWER(CONCAT('%', :query, '%'))
""", nativeQuery = true)
    List<Object[]> searchProductsByLanguageAndQuery(
            @Param("lang") String lang,
            @Param("query") String query);

    List<Product> findAll();
}
