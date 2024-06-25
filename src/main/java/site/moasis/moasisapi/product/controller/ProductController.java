package site.moasis.moasisapi.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.moasisapi.common.response.CommonResponse;
import site.moasis.moasisapi.product.dto.ProductDto;
import site.moasis.moasisapi.product.dto.ProductListDto;
import site.moasis.moasisapi.product.dto.ReqCreateProductDto;
import site.moasis.moasisapi.product.dto.ResCreateProductDto;
import site.moasis.moasisapi.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<CommonResponse<ResCreateProductDto>> createProduct(
        @RequestBody ReqCreateProductDto reqCreateProductDto
    ) {
        String productCode = productService.createProduct(reqCreateProductDto);
        ResCreateProductDto resCreateProductDto = new ResCreateProductDto(productCode);
        return CommonResponse.success(resCreateProductDto, "상품이 등록되었습니다.");
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<CommonResponse<ProductDto>> getProduct(@PathVariable("productCode") String productCode) {
        ProductDto productDto = productService.getProduct(productCode);
        return CommonResponse.success(productDto, "상품 조회 성공");
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<ProductListDto>> getProductList(
        @RequestParam("query") String query,
        @RequestParam("offset") int offset,
        @RequestParam("pageSize") int pageSize
    ) {
        ProductListDto resGetProductDto = productService.getProductList(query, offset, pageSize);
        return CommonResponse.success(resGetProductDto, "상품 조회 성공");
    }

}
