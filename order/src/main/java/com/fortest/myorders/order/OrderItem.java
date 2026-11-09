package com.fortest.myorders.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fortest.myorders.order.client.Product;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @SequenceGenerator(
            name = "order_item_id_sequence",
            sequenceName = "order_item_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_id_sequence"
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    private Integer productId;

    @Transient
    private Product product;

    private Double quantity;

    public OrderItem() {
    }

    public OrderItem(Integer id, Order order, Integer productId, Double quantity) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Integer id;
        private Order order;
        private Integer productId;
        private Product product;
        private Double quantity;

        public OrderItemBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItem build() {
            OrderItem item = new OrderItem(id, order, productId, quantity);
            item.setProduct(product);
            return item;
        }
    }
}
