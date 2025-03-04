package ma.attijari.classification_cvs_back.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OCR {
    String scanDocument(MultipartFile file) throws IOException;
}
