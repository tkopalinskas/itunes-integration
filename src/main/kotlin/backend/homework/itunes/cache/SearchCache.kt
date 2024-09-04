package backend.homework.itunes.cache

import backend.homework.itunes.cache.model.SearchResult
import org.springframework.stereotype.Component

@Component
class SearchCache {
    private var cache: MutableMap<String, SearchResult> = mutableMapOf()

    fun find(key: String): SearchResult? {
        return cache[key]
    }
    fun save(key: String, value: SearchResult) {
        cache[key] = value
    }
}