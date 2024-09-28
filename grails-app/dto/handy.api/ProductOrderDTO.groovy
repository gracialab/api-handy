package handy.api

class ProductOrderDTO {
    Integer id
    String name
    BigDecimal price
    Integer quantity

    ProductOrderDTO() {}

    ProductOrderDTO(Integer id, String name, BigDecimal price, Integer quantity) {
        this.id = id
        this.name = name
        this.price = price
        this.quantity = quantity
    }
}
