package site.moasis.moasisapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductListDto {
    private List<ProductDto> productList;
    private SliceMetaData sliceMetaData;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class SliceMetaData {
        private int totalProductQuantity;
        private boolean hasNext;
    }
}
