package site.moasis.common.client;

public interface ImageClient {
    String uploadFile(String encodedFileBase64);

    boolean deleteFile(String imageUrl);
}
