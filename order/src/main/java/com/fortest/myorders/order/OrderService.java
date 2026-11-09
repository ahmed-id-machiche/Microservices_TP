package com.fortest.myorders.order;

import com.fortest.myorders.order.client.Customer;
import com.fortest.myorders.order.client.CustomerClient;
import com.fortest.myorders.order.client.Product;
import com.fortest.myorders.order.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CustomerClient customerClient;

    // Direct method requested in TP
    public Order createOrder(Long productId, Long customerId) {
        Product product = productClient.getProductById(productId);
        Customer customer = customerClient.getCustomerById(customerId);

        OrderItem orderItem = OrderItem.builder()
                .productId(productId != null ? productId.intValue() : null)
                .quantity(1.0)
                .product(product)
                .build();

        Order order = Order.builder()
                .customerId(customerId != null ? customerId.intValue() : null)
                .customer(customer)
                .orderItems(Collections.singletonList(orderItem))
                .build();

        orderItem.setOrder(order);
        Order savedOrder = orderRepository.save(order);
        savedOrder.setCustomer(customer);
        if (savedOrder.getOrderItems() != null) {
            savedOrder.getOrderItems().forEach(item -> item.setProduct(product));
        }
        return savedOrder;
    }

    // Create a new order with items
    public Order createOrder(OrderRequest orderRequest) {
        // Fetch customer from Customer microservice via OpenFeign
        Customer customer = null;
        if (orderRequest.getCustomerId() != null) {
            try {
                customer = customerClient.getCustomerById(orderRequest.getCustomerId().longValue());
            } catch (Exception e) {
                // Keep customer null if service unreachable
            }
        }

        Customer finalCustomer = customer;
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(orderItemRequest -> {
            // Fetch product from Product microservice via OpenFeign
            Product product = null;
            if (orderItemRequest.getProductId() != null) {
                try {
                    product = productClient.getProductById(orderItemRequest.getProductId().longValue());
                } catch (Exception e) {
                    // Keep product null if service unreachable
                }
            }

            return OrderItem.builder()
                    .order(null)
                    .productId(orderItemRequest.getProductId())
                    .quantity(orderItemRequest.getQuantity())
                    .product(product)
                    .build();
        }).collect(Collectors.toList());

        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .customer(customer)
                .orderItems(orderItems)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        Order savedOrder = orderRepository.save(order);

        // Populate transient customer and product details in response
        savedOrder.setCustomer(finalCustomer);
        for (int i = 0; i < savedOrder.getOrderItems().size(); i++) {
            if (i < orderItems.size()) {
                savedOrder.getOrderItems().get(i).setProduct(orderItems.get(i).getProduct());
            }
        }

        return savedOrder;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(this::enrichOrderDetails);
        return orders;
    }

    // Get a specific order by ID
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id).map(this::enrichOrderDetails);
    }

    private Order enrichOrderDetails(Order order) {
        try {
            if (order.getCustomerId() != null) {
                order.setCustomer(customerClient.getCustomerById(order.getCustomerId().longValue()));
            }
        } catch (Exception ignored) {
        }

        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(item -> {
                try {
                    if (item.getProductId() != null) {
                        item.setProduct(productClient.getProductById(item.getProductId().longValue()));
                    }
                } catch (Exception ignored) {
                }
            });
        }
        return order;
    }

    // Update an existing order
    public Optional<Order> updateOrder(Integer id, OrderRequest orderRequest) {
        return orderRepository.findById(id).map(order -> {
            List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(orderItemRequest ->
                    OrderItem.builder()
                            .order(order)
                            .productId(orderItemRequest.getProductId())
                            .quantity(orderItemRequest.getQuantity())
                            .build()
            ).collect(Collectors.toList());
            order.setOrderItems(orderItems);
            order.setCustomerId(orderRequest.getCustomerId());
            Order updated = orderRepository.save(order);
            return enrichOrderDetails(updated);
        });
    }

    // Delete an order
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
