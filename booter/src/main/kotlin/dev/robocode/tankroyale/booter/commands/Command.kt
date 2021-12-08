package dev.robocode.tankroyale.booter.commands

import dev.robocode.tankroyale.booter.model.BotInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path

abstract class Command(private val botPaths: List<Path>) {

    protected fun getBotInfoList(botName: String): List<BotInfo> {
        val list = ArrayList<BotInfo>()
        botPaths.forEach { dirPath ->
            run {
                val path = resolveFullBotPath(dirPath, botName, "$botName.json")?.toAbsolutePath()
                if (path != null) {
                    val content = path.toFile().readText(Charsets.UTF_8)
                    list.add(Json.decodeFromString(content))
                }
            }
        }
        return list
    }

    protected fun resolveFullBotPath(botDirPath: Path, botName: String, botPath: String): Path? {
        val path = botDirPath.resolve(botName).resolve(botPath)
        return if (Files.exists(path)) path else null
    }
}