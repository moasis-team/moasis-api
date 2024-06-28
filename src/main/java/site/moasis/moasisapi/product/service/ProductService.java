package site.moasis.moasisapi.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.moasisapi.common.exception.NotFoundException;
import site.moasis.moasisapi.common.service.ImageService;
import site.moasis.moasisapi.product.dto.CreateProductRequestDTO;
import site.moasis.moasisapi.product.dto.GetProductResponseDTO;
import site.moasis.moasisapi.product.entity.Product;
import site.moasis.moasisapi.product.repository.ProductRepository;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    @Transactional()
    public String createProduct(CreateProductRequestDTO createProductRequestDTO) {
        String productCode = UUID.randomUUID().toString();
        String base64EncodedFile = Base64.getEncoder().encodeToString(createProductRequestDTO.getEncodedFile());
        String imageUrl = imageService.uploadFile(base64EncodedFile);

        Product product = Product.builder()
            .name(createProductRequestDTO.getName())
            .price(createProductRequestDTO.getPrice())
            .category(createProductRequestDTO.getCategory())
            .imageUrl(imageUrl)
            .details(createProductRequestDTO.getDetails())
            .quantity(createProductRequestDTO.getQuantity())
            .productCode(productCode)
            .productNumber(createProductRequestDTO.getProductNumber())
            .build();
        productRepository.save(product);
        return productCode;
    }

    @Transactional()
    public GetProductResponseDTO getProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        return GetProductResponseDTO.fromEntity(product);
    }

    @Transactional()
    public Slice<GetProductResponseDTO> getProductList(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<Product> response = productRepository.findAllByNameContaining(name, pageable);
        return GetProductResponseDTO.fromSlice(response);
    }
}

