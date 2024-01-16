package mg.company.varotravam.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseStorageService {

    @Value("${firebase.storage.bucket}")
    private String storageBucket; 
    private final ResourceLoader resourceLoader;

    public FirebaseStorageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String envoyerImageVersFirebaseStorage(byte[] imageBytes, String cheminSurFirebase, String contentType) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            BlobId blobId = BlobId.of(storageBucket, cheminSurFirebase);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
            Blob blob = storage.create(blobInfo, imageBytes);

            return blob.getMediaLink();
        }
    }

    public Resource telechargerImageDepuisFirebaseStorage(String cheminSurFirebase) {
        String bucketUrl = "https://firebasestorage.googleapis.com/v0/b/" + storageBucket + "/o/";
        String urlEncodedPath = cheminSurFirebase.replace("/", "%2F");
        String imageUrl = bucketUrl + urlEncodedPath;

        return resourceLoader.getResource(imageUrl);
    }
}
