package com.foodPro.demo.order.repository;

import com.foodPro.demo.member.domain.QMember;
import com.foodPro.demo.order.domain.OrderStatus;
import com.foodPro.demo.order.domain.Orders;
import com.foodPro.demo.order.domain.QOrders;
import com.foodPro.demo.order.dto.OrderDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepositorySearch {

    EntityManager em;
    JPAQueryFactory query;

    public OrderRepositorySearch(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Orders> findAll(OrderDto.OrderSearch orderSearch){

        // 변수 선언 ( static import 가능하다 )
        QOrders order = QOrders.orders;
        QMember member = QMember.member;

        return query
                .select(order) // 조회
                .from(order)
                .join(order.member, member) // order에 있는 member을 join 한다.
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberEmail()))
                .limit(1000)
                .fetch();

    }

    // 정적 Query

    /**
     * 이메일 조회
     * @param memberName
     * @return
     */
    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)){
            return null;
        }
        return QMember.member.email.like(memberName);
    }

    /**
     * 주문 상태 Order / Cancel
     * @param statusCond
     * @return
     */
    private BooleanExpression statusEq(OrderStatus statusCond){
        if(statusCond == null){
            return null; // where 문을 실행하지 않는다.
        }
        return QOrders.orders.orderStatus.eq(statusCond);
    }
}
