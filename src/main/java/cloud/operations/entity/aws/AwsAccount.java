package cloud.operations.entity.aws;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.amazonaws.services.organizations.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AwsAccount {
	@Id
	@GeneratedValue
    private Integer id;

	//AWS Account Variables Passed from SDK
	private String accountId;
	private String arn;
	private String email;
	private String name;
	private String status;
	private String joinedMethod;
	private String joinedTimeStamp;

	//Record Tracking
	private Date createDate;
	private Date modifyDate;
	private String createUser;
    private String modifyUser;

	//Relationship Mapping
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aws_organization_id")
	private AwsOrganization organization;

    //Constructors

    //Default
    public AwsAccount() {
    }

    //Variable
    public AwsAccount(Integer id, String accountId, String arn, String email, String name, String status, String joinedMethod, String joinedTimeStamp, Date createDate, Date modifyDate, String createUser, String modifyUser, AwsOrganization organization) {
        this.id = id;
        this.accountId = accountId;
        this.arn = arn;
        this.email = email;
        this.name = name;
        this.status = status;
        this.joinedMethod = joinedMethod;
        this.joinedTimeStamp = joinedTimeStamp;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.createUser = createUser;
        this.modifyUser = modifyUser;
        this.organization = organization;
    }


    //Convert AwsAccount(aws api) to AwsAccount(local)
	public AwsAccount(Account account) {
		this.setAccountId(account.getId());
		this.setArn(account.getArn());
		this.setEmail(account.getEmail());
		this.setJoinedMethod(account.getJoinedMethod());
		this.setJoinedTimeStamp(account.getJoinedTimestamp().toString());
		this.setStatus(account.getStatus());
		this.setName(account.getName());
	}

    //Getters and Setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJoinedMethod() {
        return this.joinedMethod;
    }

    public void setJoinedMethod(String joinedMethod) {
        this.joinedMethod = joinedMethod;
    }

    public String getJoinedTimeStamp() {
        return this.joinedTimeStamp;
    }

    public void setJoinedTimeStamp(String joinedTimeStamp) {
        this.joinedTimeStamp = joinedTimeStamp;
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

    public AwsOrganization getOrganization() {
        return this.organization;
    }

    public void setOrganization(AwsOrganization organization) {
        this.organization = organization;
    }

    public AwsAccount id(Integer id) {
        this.id = id;
        return this;
    }

    public AwsAccount accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public AwsAccount arn(String arn) {
        this.arn = arn;
        return this;
    }

    public AwsAccount email(String email) {
        this.email = email;
        return this;
    }

    public AwsAccount name(String name) {
        this.name = name;
        return this;
    }

    public AwsAccount status(String status) {
        this.status = status;
        return this;
    }

    public AwsAccount joinedMethod(String joinedMethod) {
        this.joinedMethod = joinedMethod;
        return this;
    }

    public AwsAccount joinedTimeStamp(String joinedTimeStamp) {
        this.joinedTimeStamp = joinedTimeStamp;
        return this;
    }

    public AwsAccount createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public AwsAccount modifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public AwsAccount createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public AwsAccount modifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
        return this;
    }

    public AwsAccount organization(AwsOrganization organization) {
        this.organization = organization;
        return this;
    }

    
    
    //Helpers

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AwsAccount)) {
            return false;
        }
        AwsAccount awsAccount = (AwsAccount) o;
        return Objects.equals(id, awsAccount.id) && Objects.equals(accountId, awsAccount.accountId) && Objects.equals(arn, awsAccount.arn) && Objects.equals(email, awsAccount.email) && Objects.equals(name, awsAccount.name) && Objects.equals(status, awsAccount.status) && Objects.equals(joinedMethod, awsAccount.joinedMethod) && Objects.equals(joinedTimeStamp, awsAccount.joinedTimeStamp) && Objects.equals(createDate, awsAccount.createDate) && Objects.equals(modifyDate, awsAccount.modifyDate) && Objects.equals(createUser, awsAccount.createUser) && Objects.equals(modifyUser, awsAccount.modifyUser) && Objects.equals(organization, awsAccount.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, arn, email, name, status, joinedMethod, joinedTimeStamp, createDate, modifyDate, createUser, modifyUser, organization);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", arn='" + getArn() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", joinedMethod='" + getJoinedMethod() + "'" +
            ", joinedTimeStamp='" + getJoinedTimeStamp() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", modifyUser='" + getModifyUser() + "'" +
            ", organization='" + getOrganization() + "'" +
            "}";
    }

}