package handy.api

class ProductDTO {
    Long id
    String name
    Double price
    Integer quantity

    ProductDTO() {}

    ProductDTO(Long id, String name, Double price, Integer quantity) {
        this.id = id
        this.name = name
        this.price = price
        this.quantity = quantity
    }
}
