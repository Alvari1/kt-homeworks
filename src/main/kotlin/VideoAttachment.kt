import Enums.AttachmentType

data class VideoAttachment(
    val item: Video
) : Attachment(AttachmentType.video)