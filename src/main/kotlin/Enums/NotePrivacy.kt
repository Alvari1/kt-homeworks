package Enums

enum class NotePrivacy(val text: String) {
    np0("все пользователи"),
    np1("только друзья"),
    np2("друзья и друзья друзей"),
    np3("только пользователь"),
}