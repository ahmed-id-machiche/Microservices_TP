package com.fortest.myorders.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fortest.myorders.order.client.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_id_sequence",
            sequenceName = "order_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_sequence"
    )
    private Integer id;

    private Integer customerId;

    @Transient
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(Integer id, Integer customerId, List<OrderItem> orderItems) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Integer id;
        private Integer customerId;
        private List<OrderItem> orderItems;
        private Customer customer;

        public OrderBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrderBuilder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public OrderBuilder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            Order order = new Order(id, customerId, orderItems);
            order.setCustomer(customer);
            return order;
        }
    }
}
