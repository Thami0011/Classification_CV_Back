package ma.attijari.classification_cvs_back.Controllers;

import ma.attijari.classification_cvs_back.Exceptions.OCRProcessingException;
import ma.attijari.classification_cvs_back.Services.OCR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequestMapping("/api")
@RestController
@CrossOrigin("http://localhost:5173/")
public class OcrController {

    @Autowired
    private OCR ocrService;


    @PostMapping("/ocr2")
    public ResponseEntity<String> extractTextFromPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String extractedText = ocrService.scanDocument(file);
            return ResponseEntity.ok().body(extractedText);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("File processing error: " + e.getMessage());
        } catch (OCRProcessingException e) {
            return ResponseEntity.internalServerError().body("OCR processing error: " + e.getMessage());
        }
    }
}


