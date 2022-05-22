import Enums.DonutEditMode

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: UInt = 0U,
    val placeholder: UInt = 0U,
    val canPublishFreeCopy: Boolean = true,
    val editMode: DonutEditMode = DonutEditMode.all
)
