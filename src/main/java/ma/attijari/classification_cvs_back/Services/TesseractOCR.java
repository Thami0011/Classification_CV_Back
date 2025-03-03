package ma.attijari.classification_cvs_back.Services;

import ma.attijari.classification_cvs_back.Exceptions.OCRProcessingException;
import ma.attijari.classification_cvs_back.config.TesseractConfig;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
@Primary
public class TesseractOCR implements OCR {

    @Autowired
    private  ITesseract tesseract;



    @Override
    public String scanDocument(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("uploaded-", ".pdf");
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        try (PDDocument document = PDDocument.load(tempFile.toFile())) {
            return extractTextFromPDF(document);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private String extractTextFromPDF(PDDocument document) {
        try {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            StringBuilder extractedText = new StringBuilder();

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.GRAY);
                extractedText.append(tesseract.doOCR(image)).append("\n");
            }
            return extractedText.toString();
        } catch (IOException | TesseractException e) {
            throw new OCRProcessingException("Error during OCR processing", e);
        }
    }
}
