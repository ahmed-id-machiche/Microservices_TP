package com.fortest.myorders.product;

public class ProductRequest {
    private Integer id;
    private String name;
    private String description;

    public ProductRequest() {
    }

    public ProductRequest(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ProductRequestBuilder builder() {
        return new ProductRequestBuilder();
    }

    public static class ProductRequestBuilder {
        private Integer id;
        private String name;
        private String description;

        public ProductRequestBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductRequest build() {
            return new ProductRequest(id, name, description);
        }
    }
}
