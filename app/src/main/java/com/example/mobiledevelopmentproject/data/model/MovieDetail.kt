package com.example.mobiledevelopmentproject.data.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val original_language: String,
    val tagline: String?,
    val runtime: Int?,
    val status: String,
    val genres: List<Genre>
)

fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        release_date = release_date,
        vote_average = vote_average,
        vote_count = vote_count,
        popularity = popularity,
        genre_ids = genres.map { it.id }, // Ambil id dari objek Genre
        adult = false, // atau sesuaikan jika kamu punya info ini
        original_title = title, // karena original_title tidak ada di MovieDetail
        original_language = original_language,
        video = false // asumsi default
    )
}
