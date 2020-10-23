//package com.foodPro.demo.item.controller;
//
//
//import com.foodPro.demo.item.dto.ItemDto;
//import com.foodPro.demo.item.service.ItemService;
//import com.foodPro.demo.member.dto.MemberDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RequestMapping("/api/item")
//@RestController
//@RequiredArgsConstructor
//public class ItemApiController {
//
//    private final ItemService itemService;
//
//    @PostMapping("/save")
//    public void save(ModelAttribute("memberForm") @Valid MemberDto.Request request,ItemDto.Request request){
//        itemService.saveItem(request);
//    }
//}
