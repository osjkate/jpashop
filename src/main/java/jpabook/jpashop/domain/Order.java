package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * persist(orderItemA)
     * persist(orderItemB)
     * persist(orderItemC)
     * persist(order);
     *
     * persist(order) 만 하면 나머지는 안해도 ㄱㅊ
     */


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

//    private Date date;  // 날짜 관련 매핑을 시켜줘야 했음
    private LocalDateTime orderDate;    // LocalDateTime 을 쓰면 하이버네이트가 알아서 잘 지원해준다.

    @Enumerated(EnumType.STRING)
    private OrderStatus status;     // 주문 상태 [ORDER, CANCEL]

    // -- 연관관계 메서드 -- //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
