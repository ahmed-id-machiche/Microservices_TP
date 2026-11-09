package com.fortest.myorders.order;

public class OrderItemRequest {
    private Integer productId;
    private Double quantity;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Integer productId, Double quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public static OrderItemRequestBuilder builder() {
        return new OrderItemRequestBuilder();
    }

    public static class OrderItemRequestBuilder {
        private Integer productId;
        private Double quantity;

        public OrderItemRequestBuilder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemRequestBuilder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemRequest build() {
            return new OrderItemRequest(productId, quantity);
        }
    }
}
