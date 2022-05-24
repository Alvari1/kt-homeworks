import Enums.ReportCommentReason
import Exceptions.CommentNotFoundException
import Exceptions.PostNotFoundException
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Before
    fun setUp() {
    }

    @Test
    fun `adding post to postlist`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "test post1"))

        assertNotNull(post1)
    }

    @Test
    fun `add like to existed post`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "test post1"))
        val post2 = WallService.likeById(post1.id)

        assertEquals(1U, post2?.likes?.count)
    }

    @Test
    fun `add like to non-existed post`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "test post1"))
        val post2 = WallService.likeById(WallService.getNextPostId() + 1U)

        assertNull(post2)
    }

    @Test
    fun `update existed post`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "test post1"))
        val updateResult = WallService.update(post1.copy(text = "test post2 updated"))

        assertTrue(updateResult)
    }

    @Test
    fun `update non-existed post`() {
        WallService.add(Post(fromId = 1U, text = "test post1"))
        val updateResult = WallService.update(Post(fromId = 1U, text = "test post1 updated"))

        assertFalse(updateResult)
    }

    @Test(expected = PostNotFoundException::class)
    fun `adding comment to non-existent post should throw PostNotFoundException exception`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "London is the capital of US."))
        WallService.createComment(
            Comment(
                id = 1U,
                fromId = 1U,
                ownerId = 1U,
                postId = post1.id + 1U,
                text = "ty nesesh' kakuyu-to ditch"
            )
        )
    }

    @Test
    fun `adding comment to existent post should return object`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "London is the capital of US."))
        val comment1 = WallService.createComment(
            Comment(
                id = 1U,
                fromId = 1U,
                ownerId = 1U,
                postId = post1.id,
                text = "ty nesesh' kakuyu-to ditch"
            )
        )
        assertNotNull(comment1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `adding report to non-existent comment should throw CommentNotFoundException exception`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "London is the capital of US."))
        val comment1 = WallService.createComment(
            Comment(
                id = 1U,
                fromId = 1U,
                ownerId = 1U,
                postId = post1.id,
                text = "ty nesesh' kakuyu-to ditch"
            )
        )

        val report1 = WallService.reportComment(
            Report(
                id = 1U,
                ownerId = 1U,
                commentId = comment1.id + 1U,
                reason = ReportCommentReason.rcr2
            )
        )
    }

    @Test
    fun `adding report to existent comment should return object`() {
        val post1 = WallService.add(Post(fromId = 1U, text = "London is the capital of US."))
        val comment1 = WallService.createComment(
            Comment(
                id = 1U,
                fromId = 1U,
                ownerId = 1U,
                postId = post1.id,
                text = "ty nesesh' kakuyu-to ditch"
            )
        )

        val report1 = WallService.reportComment(
            Report(
                id = 1U,
                ownerId = 1U,
                commentId = comment1.id,
                reason = ReportCommentReason.rcr2
            )
        )
        assertNotNull(report1)
    }
}