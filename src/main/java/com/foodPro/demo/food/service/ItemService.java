package com.foodPro.demo.food.service;

import com.foodPro.demo.catagory.Catagory;
import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.food.domain.item.Book;
import com.foodPro.demo.food.dto.ItemDto;
import com.foodPro.demo.food.repository.CatagoryRepository;
import com.foodPro.demo.food.repository.ItemRepository;
import com.foodPro.demo.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CatagoryRepository catagoryRepository;

    /**
     * FUNCTION :: 아이템 저장
     * @param
     */
    public void saveItem(ItemDto.Request request, Catagory catagory){

        switch (request.getGubun()){
            case "B": itemRepository.save(request.Book_toEntity()); // LINE :: 도서 저장
            break;
            case "F": itemRepository.save(request.Food_toEntity()); // LINE :: 음식 저장
            break;
            case "C": itemRepository.save(request.Clothes_toEntity()); // LINE :: 옷 저장
        }
    }

    /**
     * FUNCTION :: 아이템 조회
     * @return
     */
    public List<ItemDto.Response> findAllDesc(){
        return itemRepository.findAllDesc().stream()
                .map(ItemDto.Response::new)
                .collect(Collectors.toList());
    }
}
