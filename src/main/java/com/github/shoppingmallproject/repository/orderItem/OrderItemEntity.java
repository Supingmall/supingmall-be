package com.github.shoppingmallproject.repository.orderItem;


import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOptionEntity productOptionEntity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderTable;

    @Column(name = "item_amount", nullable = false)
    private Integer itemAmount;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;

    public static OrderItemEntity createOrderItem(ProductEntity product, ProductOptionEntity selectedOption, int itemAmount) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setProductOptionEntity(selectedOption);
        orderItem.setItemAmount(itemAmount);
        orderItem.setOrderPrice(product.getProductPrice() * itemAmount);

        return orderItem;
    }
    public void setProductOptionEntity(ProductOptionEntity productOptionEntity) {
        this.productOptionEntity = productOptionEntity;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void getOrderPrice(int orderPrice){
        this.orderPrice = orderPrice;
    }
}
