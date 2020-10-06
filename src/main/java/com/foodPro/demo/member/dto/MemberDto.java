package com.foodPro.demo.member.dto;

import com.foodPro.demo.config.common.Address;
import com.foodPro.demo.config.security.Role;
import com.foodPro.demo.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Getter
@AllArgsConstructor
public class MemberDto {

    @Data
    @AllArgsConstructor //LINE :: Address 필드 포함 생성자주입
    @Builder
    public static class Request{
        private String name; // LINE :: 이름
        private String pwd; // LINE :: 패스워드
        private String email; // LINE :: 이메일
        private String birth; // LINE :: 생일
        private String phone; // LINE :: 휴대폰 번호
        private String pwdChk; //LINE :: pwd 체크
        private String city;
        private String zipcode;
        private String street;
        private Address address;
        private Role role;


        public Request() {
        }

        /**
         * FUNCTION ;: 주소값 등록
         * @param zipcode
         * @param city
         * @param street
         * @return
         */
        public Address setAddress(String zipcode, String city, String street){
            return new Address(zipcode, city, street);
        }

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .pwd(pwd)
                    .birth(birth)
                    .phone(phone)
                    .address(address)
                    .build();
        }
    }

    @Getter
    public static class Response implements UserDetails {
        private Long id;
        private String name; // LINE :: 이름
        private String pwd; // LINE :: 패스워드
        private String email; // LINE :: 이메일
        private String birth; // LINE :: 생일
        private String phone; // LINE :: 휴대폰 번호
        private Address address; // LINE :: 주소
        private Role role;

        /**
         * CONSTRUCTOR :: 사용자 정보 조회
         * @param entity
         */
        public Response(Member entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.pwd = entity.getPwd();
            this.address = entity.getAddress();
            this.phone = entity.getPhone();
            this.birth = entity.getBirth();
            this.role = entity.getRole();
        }

        private Collection<? extends GrantedAuthority> authorities;

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
            return this.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
