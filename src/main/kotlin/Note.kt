import Enums.CommentNotePrivacy
import Enums.NotePrivacy

data class Note(
    val id: ULong = 0U,
    val ownerId: UInt = 0U,
    val title: String,
    val text: String,
    val date: UInt = 0U,
    var comments: UInt = 0U,
    var readComments: UInt = 0U,
    val viewUrl: String? = null,
    val privacyView: String? = null,
    val canComment: UInt = 0U,
    val textWiki: String? = null,
    val privacy: NotePrivacy = NotePrivacy.np0,
    val commentPrivacy: CommentNotePrivacy = CommentNotePrivacy.cnp0,
    val privacyComment: String? = null,
)