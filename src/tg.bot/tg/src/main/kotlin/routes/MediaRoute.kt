package routes

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.photos
import com.typesafe.config.Config
import org.koin.core.context.GlobalContext.get
import java.io.File
import java.util.UUID

fun Dispatcher.configureMediaRouting() {
    photos {
        val photoSize = message.photo!!.last()
        val bytes = bot.downloadFileBytes(photoSize.fileId)!!
        savePhoto(bytes)
    }
}

private fun savePhoto(bytes: ByteArray) {
    val koin = get()
    val config: Config = koin.get()

    val photosPath = config.getString("storage.photosPath")
    val photoFullName = "file_${UUID.randomUUID()}.jpg"                 // TODO: make unique
    val file = File("$photosPath/$photoFullName")
    if(!file.parentFile.exists()) {
        file.parentFile.createNewFile()
    }

    file.writeBytes(bytes)
}