package com.foodPro.demo.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserAccessTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    UserDetails user1(){
        return User.builder()
                .username("user1")
                .password(passwordEncoder.encode("12345"))
                .roles("GUEST").build();
    }

    UserDetails admin(){
        return User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("12345"))
                .roles("ADMIN").build();
    }

    @DisplayName("1. 사용자 권한으로 일반 페이지를 접근한다.")
//    @WithMockUser(username="user1", roles={"GUEST"})
    @Test
    public void userAccess() throws Exception {
        String resp = mockMvc.perform(get("/item/new").with(user(user1())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @DisplayName("2. 사용자는 관리자 페이지에 접근이 불가능하다.")
//    @WithMockUser(username="user1", roles={"GUEST"})
    @Test
    public void userNotAccess() throws Exception {
        mockMvc.perform(get("/admin/list").with(user(user1())))
                .andExpect(redirectedUrl("/accessDenied"));
    }


    @DisplayName("3. 관리자는 사용자페이지와 user 페이지를 접근할 수  있다.")
    @Test
//    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void adminAccess() throws Exception {
        String resp = mockMvc.perform(get("/admin/list").with(user(admin())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

//        SecurityMessage message = mapper.readValue(resp, SecurityMessage.class);
//        System.out.println("=============message============================");
//        System.out.println("message = " + message);
    }

    @DisplayName("4. login 페이지는 아무나 접근할 수 있어야 한다.")
    @Test
    public void loginAccess() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @DisplayName("5. 아이템정보는 로그인 후에 접근할 수 있다.")
    @Test
    public void itemAccess() throws Exception{
        mockMvc.perform(get("/item/list"))
                .andExpect(status().is3xxRedirection()); // login 페이지로 redirect
    }


}
