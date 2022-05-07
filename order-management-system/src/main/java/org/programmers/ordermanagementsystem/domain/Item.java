package org.programmers.ordermanagementsystem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.programmers.ordermanagementsystem.exception.NotEnoughStockException;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private ItemType type;

    public Item order(int quantity) {
        if (this.stock < quantity) {
            throw new NotEnoughStockException("재고가 부족하여 주문이 불가합니다: " + this.name + " 재고 수: " + this.stock + " 주문 수: " + quantity);
        }
        this.stock -= quantity;
        return this;
    }
}