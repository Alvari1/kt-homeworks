import Enums.AttachmentType

data class PhotoAttachment(
    val item: Photo
) : Attachment(AttachmentType.photo)