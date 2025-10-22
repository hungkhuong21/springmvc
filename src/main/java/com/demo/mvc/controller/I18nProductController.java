package com.demo.mvc.controller;

import com.demo.mvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class I18nProductController {

    private final ProductRepository productRepository;

    public I18nProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/i18n-products")
    public String i18nProducts(@RequestParam(defaultValue = "VI") String lang, Model model) {
        List<Object[]> rows = productRepository.findProductsByLanguage(lang);

        model.addAttribute("lang", lang);
        model.addAttribute("rows", rows);

        return "i18n_products";
    }
    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(defaultValue = "VI") String lang,
            @RequestParam String query,
            Model model) {

        // Kiểm tra giá trị lang
        System.out.println("Selected Language: " + lang); // Thêm log để kiểm tra
        List<Object[]> rows = productRepository.searchProductsByLanguageAndQuery(lang, query);
        model.addAttribute("lang", lang);
        model.addAttribute("rows", rows);
        model.addAttribute("searchQuery", query);

        return "i18n_products";
    }



}
