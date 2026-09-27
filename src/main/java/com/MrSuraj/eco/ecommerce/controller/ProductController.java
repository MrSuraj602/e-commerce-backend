package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Product;

import com.MrSuraj.eco.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Products", description = "Filter and retrieve the catalog. Current security configuration requires a valid JWT for these routes.")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
        @Operation(summary = "Browse and filter products", description = "Retrieves a page of catalog products filtered by category, colors, sizes, price range, minimum discount, and stock. Use it to render or refine the storefront product listing. Recognized sort values are price_low and price_high; recognized stock values are in_stock and out_of_stock. All query parameters are required by the current controller, though filter strings/lists may be empty. Pagination is zero-based.")
        @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Filtered product page returned"),
            @ApiResponse(responseCode = "400", description = "A required query parameter is missing or malformed"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Catalog query or pagination failed")
        })
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
                                           @Parameter(description = "Category name used to filter products", example = "shirts") @RequestParam String category,
                                          @Parameter(description = "Colors to include; pass the color query parameter once per color or leave it empty", example = "Blue") @RequestParam List<String>color,
                                          @Parameter(description = "Sizes to include; pass the size query parameter once per size or leave it empty", example = "M") @RequestParam List<String>size,
                                          @Parameter(description = "Minimum product price", example = "500") @RequestParam Integer minPrice,
                                          @Parameter(description = "Maximum product price", example = "3000") @RequestParam Integer maxPrice,
                                          @Parameter(description = "Minimum discount percentage", example = "10") @RequestParam Integer minDiscount,
                                          @Parameter(description = "Sort strategy; recognized values are price_low and price_high", example = "price_low", schema = @io.swagger.v3.oas.annotations.media.Schema(allowableValues = {"price_low", "price_high"})) @RequestParam String sort,
                                          @Parameter(description = "Stock filter; recognized values are in_stock and out_of_stock", example = "in_stock", schema = @io.swagger.v3.oas.annotations.media.Schema(allowableValues = {"in_stock", "out_of_stock"})) @RequestParam String stock,
                                          @Parameter(description = "Zero-based page index", example = "0") @RequestParam Integer pageNumber,
                                          @Parameter(description = "Maximum number of products in the page", example = "10") @RequestParam Integer pageSize){
        Page<Product> res = productService.getAllProduct(
                category,color,size,minPrice,maxPrice,
                minDiscount,sort,stock,pageNumber,pageSize);

        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/{productId}")
        @Operation(summary = "Get product details", description = "Loads one catalog product by its identifier. Use this when opening a product detail page or refreshing the selected product's description, price, sizes, inventory, and reviews/ratings data included by the product response.")
        @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Product returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Product lookup failed; ProductException is not mapped to a 404 response by this project")
        })
        public ResponseEntity<Product> findProductByIdHandler(@Parameter(description = "Unique identifier of the product to retrieve", example = "101") @PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
//        List<Product> products = productService.searchProduct(q);
//        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//    }
}
