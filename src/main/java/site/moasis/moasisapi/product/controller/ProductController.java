package site.moasis.moasisapi.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.moasisapi.common.response.CommonResponse;
import site.moasis.moasisapi.product.dto.CreateProductRequestDTO;
import site.moasis.moasisapi.product.dto.CreateProductResponseDTO;
import site.moasis.moasisapi.product.dto.GetProductResponseDTO;
import site.moasis.moasisapi.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<CommonResponse<CreateProductResponseDTO>> createProduct(
        @RequestBody CreateProductRequestDTO createProductRequestDTO
    ) {
        CreateProductResponseDTO response = productService.createProduct(createProductRequestDTO);
        return CommonResponse.success(response, "상품이 등록되었습니다.");
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<CommonResponse<GetProductResponseDTO>> getProduct(@PathVariable("productCode") String productCode) {
        GetProductResponseDTO response = productService.getProduct(productCode);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<Slice<GetProductResponseDTO>>> getProductList(
        @RequestParam("query") String query,
        @RequestParam("offset") int offset,
        @RequestParam("pageSize") int pageSize
    ) {
        Slice<GetProductResponseDTO> response = productService.getProductList(query, offset, pageSize);
        return CommonResponse.success(response, "상품 조회 성공");
    }

}
