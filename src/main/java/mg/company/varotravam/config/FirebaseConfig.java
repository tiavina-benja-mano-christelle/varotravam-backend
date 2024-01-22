package mg.company.varotravam.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath);

        if (serviceAccountStream == null) {
            throw new FileNotFoundException("Firebase service account file not found: " + firebaseConfigPath);
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);
        if (credentials == null) {
            throw new IOException("Failed to obtain GoogleCredentials from the provided stream.");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}
