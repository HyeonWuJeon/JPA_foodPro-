package com.foodPro.demo.member.dto;

import com.foodPro.demo.config.common.Address;
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
public class MemberDto {

    @Data
    @AllArgsConstructor //LINE :: Address 필드 포함 생성자주입
    @Builder
    public static class Request{
        @Length(max = 15) @NotBlank(message = "회원 이름을 확인해 주세요.:)")
        private String name; // LINE :: 이름

        @Length(max = 200) @NotBlank(message = "패스워드를 확인해 주세요.:)") @Pattern(regexp = "[0-9]{5,10}", message = "5~10자리의 숫자만 입력가능합니다")
        private String pwd; // LINE :: 패스워드
        private String low_pwd; // LINE :: 패스워드
        @Length(max = 200)
        private String pwdChk; // LINE :: 패스워드

        @Length(max = 200) @NotBlank(message = "이메일을 확인해 주세요.:)")
        private String email; // LINE :: 이메일

        @Length(max = 11) @NotBlank(message = "생일을 확인해 주세요.:)")

        private String birth; // LINE :: 생일

        @Length(max = 11) @NotBlank(message = "휴대폰 번호를 확인해 주세요.:)")
        private String phone; // LINE :: 휴대폰 번호

        private int age; // LINE :: 나이
        // LINE :: 주소
        @NotBlank(message = "주소를 확인해 주세요") private String city;
        @NotBlank(message = "주소를 확인해 주세요") private String zipcode;
        @NotBlank(message = "주소를 확인해 주세요") private String street;

        private Address address;

        // LINE :: 권한
        private Role role;

        public Request() {
        }

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .pwd(pwd)
                    .birth(birth)
                    .phone(phone)
                    .address(address)
                    .role(Role.GUEST)
                    .low_pwd(low_pwd)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class Response implements UserDetails {
        private Long id;
        private String name; // LINE :: 이름
        private String pwd; // LINE :: 패스워드
        private String email; // LINE :: 이메일
        private String birth; // LINE :: 생일
        private String phone; // LINE :: 휴대폰 번호
        private Address address; // LINE :: 주소
        private Role role; // LINE :: 권한
        private int age; // LINE :: 나이

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
            this.age = entity.getAge();
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

    @Getter
    @ToString
    public static class Excel {
        private String name; // LINE :: 이름
        private String email; // LINE :: 이메일
        private String birth; // LINE :: 생일
        private String phone; // LINE :: 휴대폰 번호
        private int age; // LINE :: 나이

        /**
         * Excel :: 사용자 정보 저장
         * @param entity
         */
        public Excel(Member entity) {
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.birth = entity.getBirth();
            this.age = entity.getAge();
        }
    }
}
