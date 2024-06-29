package site.moasis.moasisapi.product.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.moasisapi.common.response.CommonResponse;
import site.moasis.moasisapi.product.dto.CreateProductRequestDTO;
import site.moasis.moasisapi.product.dto.GetProductResponseDTO;
import site.moasis.moasisapi.product.dto.PatchProductRequestDTO;
import site.moasis.moasisapi.product.dto.PatchProductResponseDTO;
import site.moasis.moasisapi.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<CommonResponse<String>> createProduct(
        @Valid @RequestBody CreateProductRequestDTO createProductRequestDTO
    ) {
        String response = productService.createProduct(createProductRequestDTO);
        return CommonResponse.success(response, "상품이 등록되었습니다.");
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<CommonResponse<GetProductResponseDTO>> getProduct(@PathVariable("productCode") String productCode) {
        GetProductResponseDTO response = productService.getProduct(productCode);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<Slice<GetProductResponseDTO>>> getProductList(
        @RequestParam("query") @Nullable String query,
        @RequestParam("pageNumber") int pageNumber,
        @RequestParam("pageSize") int pageSize
    ) {
        Slice<GetProductResponseDTO> response = productService.getProductList(query, pageNumber, pageSize);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @PatchMapping("/{productCode}")
    public ResponseEntity<CommonResponse<PatchProductResponseDTO>> patchProduct(
        @PathVariable("productCode") String productCode,
        @Valid @RequestBody PatchProductRequestDTO patchProductRequestDTO
    ) {
        PatchProductResponseDTO response = productService.updateProduct(productCode, patchProductRequestDTO);
        return CommonResponse.success(response, "상품이 수정되었습니다.");
    }
}
