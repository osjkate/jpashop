package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter     // 값 타입은 보통 변경이 되지 않음 (@Setter를 제공하지 않음)
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {}
}
