package handy.api

import groovy.transform.ToString

@ToString(includeNames = true)
class UserSearchCriteria {
    String name
    String email
    BigDecimal minPurchaseAmount
    BigDecimal maxPurchaseAmount
    Date minPurchaseDate
    Date maxPurchaseDate
    Integer minPurchaseCount
    Integer maxPurchaseCount
}
