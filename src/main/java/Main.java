import cloudconverter.UploadToCloudConverter;
import com.cloudconvert.client.CloudConvertClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uploader.FileUploader;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        FileUploader processor = new FileUploader();
        var file = processor.uploadLastCreatedFile();
        LOGGER.info(String.format("The last file that was created '%s' and stored in the location %s ", file.getName(), file.getParentFile()));
        var uploadToCloudConverter = new UploadToCloudConverter(new CloudConvertClient());
        uploadToCloudConverter.uploadLocalFileToConverter(file);
    }
}
