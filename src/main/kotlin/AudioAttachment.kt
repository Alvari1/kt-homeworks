import Enums.AttachmentType

data class AudioAttachment(
    val item: Audio
) : Attachment(AttachmentType.audio)