package cloud.operations.repository.aws;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud.operations.entity.aws.AwsAccount;

public interface AwsAccountRepository extends JpaRepository<AwsAccount, Integer>{
    AwsAccount findByAccountId(String accountId);
}