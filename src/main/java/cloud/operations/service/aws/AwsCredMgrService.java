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
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.organizations.AWSOrganizations;
import com.amazonaws.services.organizations.AWSOrganizationsClientBuilder;
import com.amazonaws.services.resourcegroupstaggingapi.AWSResourceGroupsTaggingAPI;
import com.amazonaws.services.resourcegroupstaggingapi.AWSResourceGroupsTaggingAPIClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
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

    //Authenticate against MBA
    public AWSOrganizations awsAuthOrg (String key, String secret)
            throws InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		BasicAWSCredentials awsCreds = basicCredentialService(key, secret);
        
        try {
            logger.info("AWS Organization Authentication: " + awsCreds.getAWSAccessKeyId());
            return AWSOrganizationsClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(Regions.US_EAST_1)
                    .build();
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

    public AWSResourceGroupsTaggingAPI awsAuthTag (BasicSessionCredentials basicSessionCredentials, Region region) {
        try {
            return AWSResourceGroupsTaggingAPIClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                .withRegion(region.getRegionName())
                .build();
        }         
        catch(AmazonServiceException e) {
            e.printStackTrace();
            logger.info("Unable to Authenticate (AWS).  Error: " + e.toString()); 
            return null;
        }
        catch(SdkClientException e) {
            e.printStackTrace();
            logger.info("Unable to Authenticate (SDK).  Error: " + e.toString()); 
            return null;
        } 
    }    
    
    //******************************* */
    //*
    //* Assume Role Function
    //*
    //******************************* */

    public BasicSessionCredentials awsAssumeRole (BasicAWSCredentials awsCreds, String roleARN, String roleSessionName) {
        try {
            // Creating the STS client is part of your trusted code. It has
            // the security credentials you use to obtain temporary security credentials.
            // Obatin org key/pair
            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                                                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                                    .withRegion(Regions.US_EAST_1)
                                                    .build();
            // Assume the IAM role. Note that you cannot assume the role of an AWS root account;
            AssumeRoleRequest roleRequest = new AssumeRoleRequest()
                                                    .withRoleArn(roleARN)
                                                    .withRoleSessionName(roleSessionName);
            stsClient.assumeRole(roleRequest);
            
            //Create BasicAWSCredentials from STSClient
            AssumeRoleResult response = stsClient.assumeRole(roleRequest);

            return new BasicSessionCredentials(
                        response.getCredentials().getAccessKeyId(), 
                        response.getCredentials().getSecretAccessKey(), 
                        response.getCredentials().getSessionToken());            

            //return basicSessionCredentials;
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