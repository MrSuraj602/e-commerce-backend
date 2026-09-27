package com.MrSuraj.eco.ecommerce.controller;

import com.MrSuraj.eco.ecommerce.Exception.ProductException;
import com.MrSuraj.eco.ecommerce.entity.Product;
import com.MrSuraj.eco.ecommerce.request.CreateProductRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.MrSuraj.eco.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/products")
@Tag(name = "Admin Products", description = "Administrative catalog management operations. Every route requires an authenticated user with the ADMIN role.")
@SecurityRequirement(name = "bearerAuth")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
        @Operation(summary = "Create a catalog product", description = "Creates a product and resolves or creates its three category levels. Use this from the catalog-management interface when adding a product for customers. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Product body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Product or category creation failed")
        })
        public ResponseEntity<Product> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product fields and top-, second-, and third-level category names", required = true)
            @Parameter(description = "Product to add to the catalog") @Valid @RequestBody CreateProductRequest req){

        Product product = productService.createProduct(req);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
        @Operation(summary = "Delete a catalog product", description = "Deletes the product with the given identifier. Use this from catalog management when removing a product from the catalog. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Product deletion failed; ProductException is not translated to a 404 response")
        })
        public ResponseEntity<com.MrSuraj.eco.ecommerce.response.ApiResponse> deleteProduct(@Parameter(description = "Unique identifier of the product to delete", example = "101") @PathVariable Long productId) throws ProductException{
        productService.deleteProduct(productId);
        com.MrSuraj.eco.ecommerce.response.ApiResponse res = new com.MrSuraj.eco.ecommerce.response.ApiResponse();
        res.setMessage("product Deleted Successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/all")
        @Operation(summary = "List all catalog products for administration", description = "Returns all product records without storefront filtering or pagination. Use this to populate the administrative catalog-management view. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product list returned"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Catalog query failed")
        })
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
        @Operation(summary = "Update a catalog product", description = "Updates editable fields on the product with the supplied product object. Use this from catalog management to adjust product information or inventory. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product update returned"),
            @ApiResponse(responseCode = "400", description = "Product body could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Product update failed")
        })
            public ResponseEntity<Product> updateProduct(
                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product representation; the current service updates quantity only when nonzero", required = true)
                @Parameter(description = "Product fields used by the update service") @RequestBody Product req,
                @Parameter(description = "Unique identifier of the product to update", example = "101") @PathVariable Long productId)throws ProductException{
        Product product = productService.updateProduct(productId,req);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @PostMapping("/creates")
        @Operation(summary = "Create multiple catalog products", description = "Creates each product in the submitted array. Use this for a bulk catalog import or administrative batch-entry workflow. Requires the ADMIN role.")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Products created"),
            @ApiResponse(responseCode = "400", description = "Product array could not be read"),
            @ApiResponse(responseCode = "401", description = "JWT is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "One or more product creations failed")
        })
        public ResponseEntity<com.MrSuraj.eco.ecommerce.response.ApiResponse> createMultipleProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Array of product records to create", required = true)
            @Parameter(description = "Products to add in this batch") @Valid @RequestBody CreateProductRequest[] req){
        for(CreateProductRequest product : req){
            productService.createProduct(product);
        }
        com.MrSuraj.eco.ecommerce.response.ApiResponse res = new com.MrSuraj.eco.ecommerce.response.ApiResponse();
        res.setMessage("Product Created successfully!!");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }
}
