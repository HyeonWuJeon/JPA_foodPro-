package com.foodPro.demo.food.service;

import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.food.dto.ItemDto;
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

    /**
     * FUNCTION :: 아이템 저장
     * @param
     */
    protected void saveItem(ItemDto.Request request){
        itemRepository.save(request.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build().toEntity());
    }

    /**
     * FUNCTION :: 회원정보 조회
     * @return
     */
    public List<ItemDto.Response> findAllDesc(){
        return itemRepository.findAllDesc().stream()
                .map(ItemDto.Response::new)
                .collect(Collectors.toList());
    }
}
