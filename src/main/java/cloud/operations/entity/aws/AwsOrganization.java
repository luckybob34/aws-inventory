package cloud.operations.entity.aws;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.amazonaws.services.organizations.model.Organization;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AwsOrganization {
	@Id
	@GeneratedValue
    private Integer id;
 
	//Master Account Info
	private String name;
	private String arn;
	private String featureSet;
	private String orgId;
	private String masterAccountArn;
	private String masterAccountEmail;
	private String masterAccountId;
		
	//Add Policy Types Later
	
	//Master Account Key Pair
	@JsonIgnore
	private String accountKey;
	@JsonIgnore
    private String keySecret;
    @JsonIgnore 
    private String role;
	
	//Record Tracking
	private Date createDate;
	private Date modifyDate;
	private String createUser;
    private String modifyUser;
	private boolean active;


	//AWS Accounts under MBA Account
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	private List<AwsAccount> accounts;    

    //Constructors

    //Default
    public AwsOrganization() {
    }

    //Variables
    public AwsOrganization(Integer id, String name, String arn, String featureSet, String orgId, String masterAccountArn, String masterAccountEmail, String masterAccountId, String accountKey, String keySecret, String role, Date createDate, Date modifyDate, String createUser, String modifyUser, boolean active, List<AwsAccount> accounts) {
        this.id = id;
        this.name = name;
        this.arn = arn;
        this.featureSet = featureSet;
        this.orgId = orgId;
        this.masterAccountArn = masterAccountArn;
        this.masterAccountEmail = masterAccountEmail;
        this.masterAccountId = masterAccountId;
        this.accountKey = accountKey;
        this.keySecret = keySecret;
        this.role = role;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.createUser = createUser;
        this.modifyUser = modifyUser;
        this.active = active;
        this.accounts = accounts;
    }

    //Covert AwsOrganization (aws api) to AwsOrganization (local)
	public AwsOrganization(Organization organization) {
		this.setArn(organization.getArn());
		this.setFeatureSet(organization.getFeatureSet());
		this.setOrgId(organization.getId());
		this.setMasterAccountArn(organization.getMasterAccountArn());
		this.setMasterAccountEmail(organization.getMasterAccountEmail());
		this.setMasterAccountId(organization.getMasterAccountId());
	}

    //Getters and Setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getFeatureSet() {
        return this.featureSet;
    }

    public void setFeatureSet(String featureSet) {
        this.featureSet = featureSet;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMasterAccountArn() {
        return this.masterAccountArn;
    }

    public void setMasterAccountArn(String masterAccountArn) {
        this.masterAccountArn = masterAccountArn;
    }

    public String getMasterAccountEmail() {
        return this.masterAccountEmail;
    }

    public void setMasterAccountEmail(String masterAccountEmail) {
        this.masterAccountEmail = masterAccountEmail;
    }

    public String getMasterAccountId() {
        return this.masterAccountId;
    }

    public void setMasterAccountId(String masterAccountId) {
        this.masterAccountId = masterAccountId;
    }

    public String getAccountKey() {
        return this.accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getKeySecret() {
        return this.keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<AwsAccount> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<AwsAccount> accounts) {
        this.accounts = accounts;
    }

    public AwsOrganization id(Integer id) {
        this.id = id;
        return this;
    }

    public AwsOrganization name(String name) {
        this.name = name;
        return this;
    }

    public AwsOrganization arn(String arn) {
        this.arn = arn;
        return this;
    }

    public AwsOrganization featureSet(String featureSet) {
        this.featureSet = featureSet;
        return this;
    }

    public AwsOrganization orgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public AwsOrganization masterAccountArn(String masterAccountArn) {
        this.masterAccountArn = masterAccountArn;
        return this;
    }

    public AwsOrganization masterAccountEmail(String masterAccountEmail) {
        this.masterAccountEmail = masterAccountEmail;
        return this;
    }

    public AwsOrganization masterAccountId(String masterAccountId) {
        this.masterAccountId = masterAccountId;
        return this;
    }

    public AwsOrganization accountKey(String accountKey) {
        this.accountKey = accountKey;
        return this;
    }

    public AwsOrganization keySecret(String keySecret) {
        this.keySecret = keySecret;
        return this;
    }

    public AwsOrganization createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public AwsOrganization modifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public AwsOrganization createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public AwsOrganization modifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
        return this;
    }

    public AwsOrganization active(boolean active) {
        this.active = active;
        return this;
    }

    public AwsOrganization accounts(List<AwsAccount> accounts) {
        this.accounts = accounts;
        return this;
    }

    //Helpers

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AwsOrganization)) {
            return false;
        }
        AwsOrganization awsOrganization = (AwsOrganization) o;
        return Objects.equals(id, awsOrganization.id) && Objects.equals(name, awsOrganization.name) && Objects.equals(arn, awsOrganization.arn) && Objects.equals(featureSet, awsOrganization.featureSet) && Objects.equals(orgId, awsOrganization.orgId) && Objects.equals(masterAccountArn, awsOrganization.masterAccountArn) && Objects.equals(masterAccountEmail, awsOrganization.masterAccountEmail) && Objects.equals(masterAccountId, awsOrganization.masterAccountId) && Objects.equals(accountKey, awsOrganization.accountKey) && Objects.equals(keySecret, awsOrganization.keySecret) && Objects.equals(role, awsOrganization.role) && Objects.equals(createDate, awsOrganization.createDate) && Objects.equals(modifyDate, awsOrganization.modifyDate) && Objects.equals(createUser, awsOrganization.createUser) && active == awsOrganization.active && Objects.equals(accounts, awsOrganization.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, arn, featureSet, orgId, masterAccountArn, masterAccountEmail, masterAccountId, accountKey, keySecret, role, createDate, modifyDate, createUser, modifyUser, active, accounts);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", arn='" + getArn() + "'" +
            ", featureSet='" + getFeatureSet() + "'" +
            ", orgId='" + getOrgId() + "'" +
            ", masterAccountArn='" + getMasterAccountArn() + "'" +
            ", masterAccountEmail='" + getMasterAccountEmail() + "'" +
            ", masterAccountId='" + getMasterAccountId() + "'" +
            ", accountKey='" + getAccountKey() + "'" +
            ", keySecret='" + getKeySecret() + "'" +
            ", role='" + getRole() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", modifyUser='" + getModifyUser() + "'" +
            ", active='" + isActive() + "'" +
            ", accounts='" + getAccounts() + "'" +
            "}";
    }


}