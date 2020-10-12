package com.sky7th.devtimeline.api.generic.mail.domain.token;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EmailVerificationTokenRepository extends CrudRepository<EmailVerificationToken, String> {

  List<EmailVerificationToken> findAllByUserId(Long id);
}
