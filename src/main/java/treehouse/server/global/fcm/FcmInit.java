package treehouse.server.global.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
@Slf4j
public class FcmInit {
    @Value("${firebase.admin-sdk}")
    private String FIREBASE_KEY_PATH;

    @PostConstruct
    public void firebaseMessaging() throws IOException {

        String base64KeyString = FIREBASE_KEY_PATH;
        byte[] decodedKeyString = Base64.getDecoder().decode(base64KeyString);
        InputStream credentialStream = new ByteArrayInputStream(decodedKeyString);

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialStream))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
