package com.example.mvcframworkmake.springmvcFinal.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    // Integer 한 이유는 초기값 미설정 때문,
    // Integer 미설정 시 null, int 미설정 시 오류
    private Integer price;
    private Integer quantity;

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
