package com.foodPro.demo.item.controller;

import com.foodPro.demo.config.common.pagging.PageWrapper;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.service.ItemService;
import com.foodPro.demo.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@RequestMapping("/item")
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/list")
    public String findAllDesc(Model model, Pageable pageable){
        Page<ItemDto.Response> responses = itemService.findAllDesc(pageable);
        PageWrapper<ItemDto.Response> page = new PageWrapper<>(responses, "/item/list");
        model.addAttribute("list",responses);
        model.addAttribute("page",page);
        return"item/list";
    }

    @GetMapping("/new")
    public String save(Model model){
        model.addAttribute("itemForm", new ItemDto.Request());
        return "item/new";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("itemForm") @Valid ItemDto.Request request, BindingResult result){
        if(result.hasErrors()){
            return "item/new";
        }
        itemService.saveItem(request);
        return "item/list";
    }



}
