package com.foodPro.demo.member.dto;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.common.BaseTimeEntity;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;


@Getter
public class MemberDto extends BaseTimeEntity {

    @Data
    //UNIT TEST 어노테이션
    @NoArgsConstructor
    public static class Request{

        private Long id;
        @Length(max = 200) @NotBlank(message = "패스워드를 확인해 주세요.:)") @Pattern(regexp = "[0-9]{5,10}", message = "5~10자리의 숫자만 입력가능합니다")
        private String pwd; // LINE :: 패스워드
        private String low_pwd; // LINE :: 패스워드

        @Length(max = 200)
        private String pwdChk; // LINE :: 패스워드

        @Length(max = 200) @NotBlank(message = "이메일을 확인해 주세요.:)")
        private String email; // LINE :: 이메일

        private Role role;

        private Address address;

        // LINE :: 주소
        @NotBlank(message = "주소를 확인해 주세요") private String city;
        @NotBlank(message = "주소를 확인해 주세요") private String zipcode;
        @NotBlank(message = "주소를 확인해 주세요") private String street;

        public void setAddress(String city, String street, String zipcode) {
            this.address = new Address(city, zipcode, street);
        }

        @Builder
        public Request(Role role, @Length(max = 200) @NotBlank(message = "패스워드를 확인해 주세요.:)") @Pattern(regexp = "[0-9]{5,10}", message = "5~10자리의 숫자만 입력가능합니다") String pwd, String low_pwd, @Length(max = 200) String pwdChk, @Length(max = 200) @NotBlank(message = "이메일을 확인해 주세요.:)") String email,  @NotBlank(message = "주소를 확인해 주세요") String city, @NotBlank(message = "주소를 확인해 주세요") String zipcode, @NotBlank(message = "주소를 확인해 주세요") String street, Address address) {
            this.pwd = pwd;
            this.low_pwd = low_pwd;
            this.pwdChk = pwdChk;
            this.email = email;
            this.city = city;
            this.zipcode = zipcode;
            this.street = street;
            this.address =address;
            this.role =role;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .pwd(pwd)
                    .address(address)
                    .role(Role.ADMIN)
                    .low_pwd(low_pwd)
                    .enabled(true)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class Response implements UserDetails, GrantedAuthority {
        private Long id;
        private String pwd; // LINE :: 패스워드
        private String email; // LINE :: 이메일
        private Address address; // LINE :: 주소
        private Role role; // LINE :: 권한
        private String authority; // LINE :: 인증
        private boolean enabled; // LINE :: USER 상태확인
        /**
         * CONSTRUCTOR :: 사용자 정보 조회
         * @param entity
         */
        public Response(Member entity) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.pwd = entity.getPwd();
            this.address = entity.getAddress();
            this.role = entity.getRole();
            this.enabled = entity.isEnabled();
        }

        private Collection<? extends GrantedAuthority> authorities; // user 권한

        /**
         * CONSTRUCTOR :: 사용자 정보 인증
         * @param u
         * @param singleton
         * @param <T>
         */
        public <T> Response(Member u, Set<T> singleton) {
            this.email = u.getEmail();
            this.pwd = u.getPwd();
            this.authorities = (Collection<? extends GrantedAuthority>) singleton;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return this.getPwd();
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return enabled;
        } // 계정이 만기되었는지 확인

        @Override
        public boolean isAccountNonLocked() {
            return enabled;
        } // 계정이 잠겨있는지 확인

        @Override
        public boolean isCredentialsNonExpired() {
            return enabled;
        } // 자격이 만기되었는지 확인

//        @Override
//        public boolean isEnabled() {
//            return true;
//        }



    }
}
