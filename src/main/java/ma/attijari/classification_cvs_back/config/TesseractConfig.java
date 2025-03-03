package ma.attijari.classification_cvs_back.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Bean
    public ITesseract getTesseractInstance() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\thami\\AppData\\Local\\Programs\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("eng+fra");
        tesseract.setOcrEngineMode(1);
        tesseract.setPageSegMode(3);
        return tesseract;
    }
}