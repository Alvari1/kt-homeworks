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
        val post2 = WallService.likeById(WallService.getNextId() + 1U)

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
}