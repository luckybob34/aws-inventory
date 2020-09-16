package cloud.operations.service.aws;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.amazonaws.services.organizations.AWSOrganizations;
import com.amazonaws.services.organizations.model.DescribeOrganizationRequest;
import com.amazonaws.services.organizations.model.DescribeOrganizationResult;
import com.amazonaws.services.organizations.model.Organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.operations.entity.aws.AwsOrganization;
import cloud.operations.repository.aws.AwsOrganizationRepository;
import cloud.operations.service.util.EncryptionService;

@Service
public class AwsOrganizationService {

	// Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Format Date for Logging
	//private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	// Add repository for AWS Master Account
	@Autowired
	private AwsOrganizationRepository awsOrganizationRepository;

	// Add Services
	@Autowired
	private AwsAccountService awsAccountService;

	@Autowired
	private AwsCredMgrService awsCredMgrService;

	@Autowired
	private EncryptionService encryptionService;

	/*
	 * 
	 * Access Repository defaults
	 * 
	 */

	/*
	 * 
	 * Object access to Repository
	 * 
	 */

	// Save new MBA account for Organization
	public void createOrganization(AwsOrganization awsOrganization)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        
        AwsOrganization temp = descibleOrganization(awsOrganization.getAccountKey(), awsOrganization.getKeySecret());
        
        temp.setAccountKey(encryptionService.encrypt(awsOrganization.getAccountKey()));
        temp.setKeySecret(encryptionService.encrypt(awsOrganization.getKeySecret()));
        temp.setName(awsOrganization.getName());
        temp = save(temp);
        awsAccountService.describeAccounts(temp);
        temp.setCreateDate(new Date());
        temp = save(temp);
    }

    //Save Organization to DB
    public AwsOrganization save(AwsOrganization organization)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {

		//If Organization exists update/save
		if (organization.getId() != null) {
			organization.setModifyDate(new Date());
			organization.setModifyUser("admin");
			logger.info("Organization Updated: " + organization.getName());
			return awsOrganizationRepository.save(organization);
		} else if (organization.getId() == null) {

			AwsOrganization temp = descibleOrganization(organization.getAccountKey(), organization.getKeySecret());

			temp.setCreateDate(new Date());
			temp.setCreateUser("admin");
			temp.setRole(organization.getRole());
			temp.setName(organization.getName());
			temp.setAccountKey(encryptionService.encrypt(organization.getAccountKey()));
			temp.setKeySecret(encryptionService.encrypt(organization.getKeySecret()));

			awsOrganizationRepository.save(temp);
			logger.info("Organization Created: " + organization.getName());

			awsAccountService.describeAccounts(temp);
			
			return awsOrganizationRepository.save(temp);
		} else {
            logger.info("Something went wrong saving the Organization!");
            logger.debug("Organization: " + organization.toString());
            return null;
		}       
    }

	/*
	 * 
	 * AWS SDK Calls to Populate Data
	 * 
	 */
	
	//Authenticate to AWS and find Organization info
	public AwsOrganization descibleOrganization (String key, String secret) {
		
		//Connect to AWS Organizations SDK
		AWSOrganizations orgClient = awsCredMgrService.awsAuthOrg(key, secret);
		
		//Request Organizations from AWS SDK
		DescribeOrganizationRequest request = new DescribeOrganizationRequest();
		
		//Get Organization Results from AWS SDK
		DescribeOrganizationResult result = orgClient.describeOrganization(request);
		
		//Parse Results (should only ever be one)
		Organization org =  result.getOrganization();
		logger.info("AWS Organization found");
		//Return the new data
		return new AwsOrganization(org);
	}    
}