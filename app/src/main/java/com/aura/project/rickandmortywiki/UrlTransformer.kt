package com.aura.project.rickandmortywiki

class UrlTransformer {
    companion object {
        private const val char_scheme = "https://rickandmortyapi.com/api/character/"
        private const val episode_scheme = "https://rickandmortyapi.com/api/episode/"
        private const val location_scheme = "https://rickandmortyapi.com/api/location/"

        fun urlsToIdArray(urls: List<String>) = urls
            .map { url -> url.substringAfter(char_scheme, "") }
            .filter { idString -> idString.isNotEmpty() }
            .map { it.toLong() }
            .toLongArray()

        fun extractEpisodeRequestPath(urls: List<String>) = urls
            .map { url -> url.substringAfter(episode_scheme, "") }
            .filter { idString -> idString.isNotEmpty() }
            .reduce { acc, id -> "$acc, $id" }

        fun getRequestPath(ids: LongArray) = ids.joinToString(separator = ",", transform = { it.toString() })

        fun extractIdFromLocationUrl(url: String) = url.substringAfter(location_scheme, "").toLong()
    }
}
