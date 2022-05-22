fun main() {
    val post1 = WallService.add(Post(fromId = 1U, text = "test post1"))
    val post2 = WallService.add(Post(fromId = 2U, text = "test post2"))

    if (post1 != null) {
        WallService.likeById(post1.id)
    }

    if (post2 != null) {
        WallService.update(post2.copy(text = "test post2 updated"))
    }
}