package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.model.article

import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import com.livingTechUSA.thatsnewstome.model.article.Article

fun ArticleRaw.toNewModel(): Article {
    val id = source.id ?: "ID"
    val name = source.name ?: "NAME"
    return Article(
        id,
        name,
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )
}


fun Article.toEntity(): ArticleEntity {

    return  ArticleEntity(
        0L,
        id,
        name,
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )
}
fun ArticleEntity.toModel(): Article {
    return  Article(
        id,
        name,
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )
}