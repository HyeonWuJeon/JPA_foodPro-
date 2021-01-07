package com.foodPro.demo.item;

import com.foodPro.demo.item.domain.item.Book;
import com.foodPro.demo.item.dto.ItemDto;
import com.foodPro.demo.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class ItemServiceApiTest {


    @Autowired
    private ItemService itemService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 아이템_저장(){
        //given
        String name = "나미야잡화점";
        int price = 10000;
        int stock = 22;
        String author = "히가시노게이고";
        ItemDto.Request request = new ItemDto.Request();
        request.setPrice(price);
        request.setName(name);
        request.setAuthor(author);
        request.setGubun("Book");
        request.setStockQuantity(stock);
        //when
        itemService.saveItem(ItemDto.Request.builder()
                        .price(price)
                        .name(name)

                        .author(author)
                        .gubun("Book")
                        .stockQuantity(stock).build());
        //then
        Book book = em.find(Book.class, 1L);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertEquals(book.getName(), name,() ->"name은 나미야잡화점 이어야 한다.");
        assertEquals(book.getStockQuantity(), stock,() ->"수량은 22개 여야한다.");

    }

}
