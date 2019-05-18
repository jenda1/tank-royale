package net.robocode2.gui.model

data class BotListUpdate(val bots: Set<BotInfo>)
    : Message(MessageType.BOT_LIST_UPDATE.type)