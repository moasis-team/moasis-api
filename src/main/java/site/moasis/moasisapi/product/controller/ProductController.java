package site.moasis.moasisapi.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.moasisapi.common.response.CommonResponse;
import site.moasis.moasisapi.product.dto.ReqCreateProductDto;
import site.moasis.moasisapi.product.dto.ResCreateProductDto;
import site.moasis.moasisapi.product.dto.ResGetProductDto;
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
    public ResponseEntity<CommonResponse<ResGetProductDto>> getProduct(@PathVariable("productCode") String productCode) {
        ResGetProductDto resGetProductDto = productService.getProduct(productCode);
        return CommonResponse.success(resGetProductDto, "상품 조회 성공");
    }
}
