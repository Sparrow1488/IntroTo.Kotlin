package routes

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.photos
import java.io.File

fun Dispatcher.configureMediaRouting() {
    photos {
        val photoSize = message.photo!!.last()
        val bytes = bot.downloadFileBytes(photoSize.fileId)
        File("fromchat.png").writeBytes(bytes!!)
    }
}