package handy.api

import grails.converters.JSON
import grails.gorm.transactions.Transactional


class NoveltyController {

    NoveltyService noveltyService

    def index() {

    }

    @Transactional
    def save() {
        def request = request.JSON
        def response  = noveltyService.saveNovelty(request)
        if (response.("valid")){
            render(contentType: 'application/json', status: 201, response as JSON)
        } else {
            render(contentType: 'application/json', status: 400, response as JSON)
        }

    }
}