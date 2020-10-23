package com.foodPro.demo.item.service;

import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.repository.CatagoryRepository;
import com.foodPro.demo.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CatagoryRepository catagoryRepository;

    /**
     * FUNCTION :: 아이템 저장, 추후 카테고리추가
     * @param
     */
    public void saveItem(ItemDto.Request request){

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

        //LINE :: 검색기능 => 카테고리 + 페이징
        return itemRepository.findAllDesc().stream()
                .map(ItemDto.Response::new)
                .collect(Collectors.toList());
    }

    /**
     * FUNCTION :: 아이템 개별 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public ItemDto.Response findById(Long id) {
        Item entity = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new ItemDto.Response(entity);
    }
}
