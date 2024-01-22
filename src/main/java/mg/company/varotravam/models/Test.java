package mg.company.varotravam.models;

import org.springframework.web.multipart.MultipartFile;

public class Test {
    String fileName;
    byte[] fileValue;
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getFileValue() {
        return fileValue;
    }
    public void setFileValue(byte[] fileValue) {
        this.fileValue = fileValue;
    }

    
}
