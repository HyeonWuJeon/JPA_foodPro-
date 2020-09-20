package com.foodPro.demo.config.aop;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TransactionAop {

    private final PlatformTransactionManager transactionManager; // LINE :: 각 DB 설정에서 설정한 PlatformTransactionManager 객체

    private static final String EXPRESSION = "execution(* com.foodPro.demo..service.*Service.*(..)))"; // LINE :: 포인트 컷

    @Bean
    public TransactionInterceptor transactionAdvice() {
        List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class)); // LINE :: 트랜잭션 롤백 규칙 선언

        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(rollbackRules);           // LINE :: 트랜잭션 롤백 규칙 설정
        transactionAttribute.setName("*");                              // LINE :: 트랜잭션 이름
        transactionAttribute.setIsolationLevelName("default");          // LINE :: 격리 규칙
        transactionAttribute.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW"); // LINE :: 전파지연

        MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
        attributeSource.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor((TransactionManager) transactionManager, attributeSource);
    }
}
