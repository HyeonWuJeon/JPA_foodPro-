package com.foodPro.demo.ApiTest;

import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.food.domain.item.Book;
import com.foodPro.demo.food.dto.ItemDto;
import com.foodPro.demo.food.repository.ItemRepository;
import com.foodPro.demo.food.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceApiTest {


    @Autowired
    private ItemService itemService;

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(false)
    public void 아이템_저장(){
        //given
        String name = "나미야잡화점";
        int price = 10000;
        int stock = 22;
        String author = "히가시노게이고";
        ItemDto.Request request = new ItemDto.Request("B",name, price, stock, author);
        //when
        itemService.saveItem(request);
        //then
        Book book = em.find(Book.class, 1L);
        assertThat(book.getAuthor()).isEqualTo(author);
    }

}
