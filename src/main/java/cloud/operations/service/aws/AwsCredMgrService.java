package cloud.operations.service.aws;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.regions.Regions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.operations.service.util.EncryptionService;

@Service
public class AwsCredMgrService {

    // Log output to console
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EncryptionService encryptionService;

    // AWS Basic Credential Service
    public BasicAWSCredentials basicCredentialService(String key, String secret)
            throws InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        return new BasicAWSCredentials(encryptionService.decrypt(key), encryptionService.decrypt(secret));
    }


    //List Regions for AWS
	public List<Region> getRegions (BasicAWSCredentials awsCreds) {
        try {
            AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.US_EAST_1)
            .build();

            logger.info("Fetched Regions");

            return 	ec2Client.describeRegions().getRegions();
        } catch(AmazonServiceException e) {
            e.printStackTrace();
            logger.info("Unable to Authenticate (AWS).  Error: " + e.toString()); 
            return null;
        } catch(SdkClientException e) {
            e.printStackTrace();
            logger.info("Unable to Authenticate (SDK).  Error: " + e.toString()); 
            return null;
        } 		
    }
}