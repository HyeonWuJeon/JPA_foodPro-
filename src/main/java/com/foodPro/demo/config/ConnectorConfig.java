package com.foodPro.demo.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {

//        https://www.drissamri.com/blog/java/enable-https-in-spring-boot/ :: step3

        // 내장 톰캣 팩토리 생성
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            @Override // ㅗ
            protected void postProcessContext(Context context) { // 톰캣실행전 context 재정의
                SecurityConstraint securityConstraint = new SecurityConstraint(); // 보안설정, 단일스레드 동기화x
                securityConstraint.setUserConstraint("CONFIDENTIAL"); // 사용자 제약조건 설정
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*"); //모든 요청에 적용
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint); // 정의된 context 추가
            }
        };
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    // redirect
    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }
}
