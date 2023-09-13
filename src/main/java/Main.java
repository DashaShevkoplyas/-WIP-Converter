import com.cloudconvert.client.CloudConvertClient;
import com.cloudconvert.client.setttings.StringSettingsProvider;
import com.cloudconvert.exception.CloudConvertClientException;
import com.cloudconvert.exception.CloudConvertServerException;
import uploader.FileUploader;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, CloudConvertServerException, CloudConvertClientException, URISyntaxException {
        FileUploader processor = new FileUploader();
        var file = processor.uploadLastCreatedFile();
        System.out.println("The last file that was created: " + file.getName());
        final CloudConvertClient cloudConvertClient =
                new CloudConvertClient(new StringSettingsProvider("api-key", "webhook-signing-secret", false));
        System.out.println(cloudConvertClient.tasks().list());
    }
}
