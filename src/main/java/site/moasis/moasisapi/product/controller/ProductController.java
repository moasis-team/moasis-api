package site.moasis.moasisapi.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moasis.moasisapi.common.response.CommonResponse;
import site.moasis.moasisapi.product.dto.ProductReqDto;
import site.moasis.moasisapi.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<CommonResponse<String>> createProduct(
        @RequestBody ProductReqDto productReqDto
    ) {
        String productCode = productService.createProduct(productReqDto);
        return CommonResponse.success(productCode, "상품이 등록되었습니다.");
    }
}
