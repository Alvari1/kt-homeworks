object WallService {
    private var posts = emptyArray<Post>()
    private var nextId: ULong = 1U

    fun getNextId(): ULong {
        return nextId
    }

    fun add(post: Post): Post {
        posts += post.copy(id = nextId, date = (System.currentTimeMillis() / 1000).toUInt())
        nextId++
        return posts.last()
    }

    fun likeById(id: ULong): Post? {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(likes = post.likes.copy(count = post.likes.count + 1U))
                return posts[index]
            }
        }
        return null
    }

    fun update(post: Post): Boolean {
        for ((index, posted) in posts.withIndex()) {
            if (posted.id == post.id) {
                posts[index] = post.copy(fromId = posted.fromId, date = posted.date)
                return true
            }
        }
        return false
    }
}