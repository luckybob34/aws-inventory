package cloud.operations.repository.aws;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud.operations.entity.aws.AwsOrganization;

public interface AwsOrganizationRepository extends JpaRepository<AwsOrganization, Integer>{

	AwsOrganization findByAccountKey(String accountKey);

	AwsOrganization findByName(String name);


}
