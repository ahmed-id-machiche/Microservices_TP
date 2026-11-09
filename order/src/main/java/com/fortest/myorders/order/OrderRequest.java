package com.fortest.myorders.order;

import java.util.List;

public class OrderRequest {
    private Integer customerId;
    private List<OrderItemRequest> orderItems;

    public OrderRequest() {
    }

    public OrderRequest(Integer customerId, List<OrderItemRequest> orderItems) {
        this.customerId = customerId;
        this.orderItems = orderItems;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }

    public static OrderRequestBuilder builder() {
        return new OrderRequestBuilder();
    }

    public static class OrderRequestBuilder {
        private Integer customerId;
        private List<OrderItemRequest> orderItems;

        public OrderRequestBuilder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderRequestBuilder orderItems(List<OrderItemRequest> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public OrderRequest build() {
            return new OrderRequest(customerId, orderItems);
        }
    }
}
