package com.foodPro.demo.item.service;

import com.foodPro.demo.item.domain.Item;
import com.foodPro.demo.item.domain.item.Book;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.repository.CatagoryRepository;
import com.foodPro.demo.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<ItemDto.Response> findAllDesc(Pageable pageable){
         //책 / 음식 / 옷
        //LINE :: 검색기능 => 카테고리 + 페이징
        return itemRepository.findAllDesc(pageable).map(ItemDto.Response::new);
    }

    /**
     * FUNCTION :: 아이템 개별 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public ItemDto.Response findById(Long id) {
        Book entity = (Book) itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        return new ItemDto.Response(entity);
    }

    /**
     * FUNCTION :: 상품 수정 ==> dirty checking
     * @param author
     * @param name
     * @param price
     * @param stockQuantity
     */
    public void update(Long id, String author, String name, int price, int stockQuantity) {
        Book entity = (Book) itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        entity.update(author,name,price,stockQuantity);
    }
}
