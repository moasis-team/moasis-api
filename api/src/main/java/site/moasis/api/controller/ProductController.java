package site.moasis.api.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.moasis.api.response.CommonResponse;
import site.moasis.api.service.NotificationService;
import site.moasis.api.service.ProductService;
import site.moasis.common.dto.CreateProductRequestDTO;
import site.moasis.common.dto.GetProductResponseDTO;
import site.moasis.common.dto.PatchProductRequestDTO;
import site.moasis.common.dto.PatchProductResponseDTO;
import site.moasis.common.entity.Product;
import site.moasis.common.enums.NotificationType;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final NotificationService notificationService;

    @PostMapping()
    public ResponseEntity<CommonResponse<String>> createProduct(
        @Valid @RequestBody CreateProductRequestDTO createProductRequestDTO
    ) {
        Product product = productService.createProduct(createProductRequestDTO);
        String response = product.getProductCode();
        notificationService.createNotification(
            NotificationType.CREATE,
            product,
            product.getQuantity()
        );
        return CommonResponse.success(response, "상품이 등록되었습니다.");
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<CommonResponse<GetProductResponseDTO>> getProduct(@PathVariable("productCode") String productCode) {
        GetProductResponseDTO response = productService.getProduct(productCode);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<Slice<GetProductResponseDTO>>> getProductList(
        @RequestParam("name") @Nullable String name,
        @RequestParam("category") @Nullable String category,
        @RequestParam("productNumber") @Nullable String productNumber,
        @RequestParam("pageNumber") int pageNumber,
        @RequestParam("pageSize") int pageSize
    ) {
        Slice<GetProductResponseDTO> response = productService.getProductList(name, category, productNumber, pageNumber, pageSize);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @PatchMapping("/{productCode}")
    public ResponseEntity<CommonResponse<PatchProductResponseDTO>> patchProduct(
        @PathVariable("productCode") String productCode,
        @Valid @RequestBody PatchProductRequestDTO patchProductRequestDTO
    ) {
        Product product = productService.updateProduct(productCode, patchProductRequestDTO);
        PatchProductResponseDTO response = PatchProductResponseDTO.of(product);
        notificationService.createNotification(
            NotificationType.UPDATE,
            product,
            0
        );
        return CommonResponse.success(response, "상품이 수정되었습니다.");
    }

    @DeleteMapping("/{productCode}")
    public ResponseEntity<CommonResponse<String>> deleteProduct(@PathVariable("productCode") String productCode) {
        Product product = productService.deleteProduct(productCode);
        String response = product.getProductCode();
        notificationService.createNotification(
            NotificationType.DELETE,
            product,
            0
        );
        return CommonResponse.success(response, "상품이 삭제되었습니다.");
    }
}
