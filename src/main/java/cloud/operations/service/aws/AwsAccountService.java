package cloud.operations.service.aws;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.operations.entity.aws.AwsAccount;
import cloud.operations.entity.aws.AwsOrganization;
import cloud.operations.repository.aws.AwsAccountRepository;
import cloud.operations.service.util.EncryptionService;

import com.amazonaws.services.organizations.AWSOrganizations;
import com.amazonaws.services.organizations.model.Account;
import com.amazonaws.services.organizations.model.ListAccountsRequest;
import com.amazonaws.services.organizations.model.ListAccountsResult;

@Service
public class AwsAccountService {
	
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Add Repositories
	@Autowired
    private AwsAccountRepository awsAccountRepository;
 
	@Autowired
	private EncryptionService encryptionService;

	@Autowired
	private AwsCredMgrService awsCredMgrService;    

	/*
	 * 
	 * Access Repository defaults
	 * 
	 */
	//Find One Account by ID
	public AwsAccount findById(Integer id) {
		return awsAccountRepository.findById(id).get();
    }
    
    public List<AwsAccount> findAll() {
        return awsAccountRepository.findAll();
    }

	/*
	 * 
	 * Object access to Repository
	 * 
	 */
    
     //Save Account to Organization
	public AwsAccount save(AwsAccount account) {

        //If account exists, update/save
        if (account.getId() != null) {
            account.setModifyDate (new Date());
            account.setModifyUser("admin");
            logger.info("Account Updated: " + account.getName());        
            return awsAccountRepository.save(account);
        //Elese If create new account/save
        } else if (account.getId() == null) {
            account.setCreateDate(new Date());
            account.setCreateUser("admin");
            logger.info("Account Created: " + account.getName());  
            return awsAccountRepository.save(account);
        //Else Error return null
        } else {
            logger.info("Something went wrong saving the account!");
            logger.debug("Account: " + account.toString());
            return null;
        }

    }
    
    public AwsAccount refresh (AwsAccount account) {
            describeAccounts(organization);
        return account;
    }
	    
	/*
	 * 
	 * AWS SDK Calls to Populate Data
	 * 
	 */
	
	//Authenticate to AWS and find Account Information from Organization
	public void describeAccounts (AwsOrganization organization) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
				
		//Connect to AWS Organizations SDK
		AWSOrganizations orgClient = awsCredMgrService.awsAuthOrg(encryptionService.decrypt(organization.getAccountKey()), encryptionService.decrypt(organization.getKeySecret()));
		
		//Iterating through Accounts
		boolean done = false;
		
		//Temp AwsAccount List to Iterate Through
		List<AwsAccount> tempAccounts = new ArrayList<AwsAccount>();
        		
		//Request List of Accounts
        ListAccountsRequest request = new ListAccountsRequest();
        
		while(!done) {
			ListAccountsResult response = orgClient.listAccounts(request);
			for (Account account : response.getAccounts()) {
                
                AwsAccount temp = new AwsAccount(account);
                AwsAccount exists = awsAccountRepository.findByAccountId(temp.getAccountId());
                
                if (exists != null) {
                    exists.setArn(temp.getArn());
                    exists.setEmail(temp.getEmail());
                    exists.setName(temp.getName());
                    exists.setStatus(temp.getStatus());
                    exists.setJoinedMethod(temp.getJoinedMethod());
                    exists.setJoinedTimeStamp(temp.getJoinedTimeStamp());
                }

                exists.setOrganization(organization);
				tempAccounts.add(exists);
				save(exists);
			}
			
			request.setNextToken(response.getNextToken());
			
			if (response.getNextToken() == null) {
				done = true;
			}
			
		}
		organization.setAccounts(tempAccounts);
		
	}

}