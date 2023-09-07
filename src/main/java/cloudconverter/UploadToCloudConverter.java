package cloudconverter;

import com.cloudconvert.client.CloudConvertClient;
import com.cloudconvert.dto.request.UploadImportRequest;
import com.cloudconvert.exception.CloudConvertClientException;
import com.cloudconvert.exception.CloudConvertServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Class that is responsible for uploading file to the Cloud Converter.
 */
public class UploadToCloudConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadToCloudConverter.class);

    private final CloudConvertClient cloudConvertClient;

    public UploadToCloudConverter(CloudConvertClient cloudConvertClient) {
        this.cloudConvertClient = cloudConvertClient;
    }

    public void uploadLocalFileToConverter(File localFile) throws IOException, URISyntaxException {
        try {
            var inputStream = new FileInputStream(localFile);
            var uploadImportTaskResponse = cloudConvertClient.importUsing().upload(new UploadImportRequest(), inputStream, localFile.getName()).getBody();
            if (uploadImportTaskResponse == null || uploadImportTaskResponse.getId() == null) {
                LOGGER.error("Import/upload task has failed, returned result is null");
                return;
            }
            var waitForTaskToComplete = cloudConvertClient.tasks().wait(uploadImportTaskResponse.getId()).getBody();
            if (waitForTaskToComplete == null) {
                LOGGER.error("Failed to complete the task");
                return;
            }
            LOGGER.info("File successfully uploaded: " + waitForTaskToComplete.getLinks().getSelf());
        } catch (CloudConvertClientException | CloudConvertServerException exception) {
            LOGGER.error("Failed to create an instance of cloud converter due to the following reason: " + exception.getMessage());
        }
    }
}
