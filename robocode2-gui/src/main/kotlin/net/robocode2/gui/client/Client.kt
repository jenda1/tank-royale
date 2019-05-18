package net.robocode2.gui.client

import com.beust.klaxon.Klaxon
import net.robocode2.gui.model.*
import net.robocode2.gui.utils.Event
import java.net.URI

object Client : AutoCloseable {

    val defaultUri = URI("ws://localhost:55000")

    // public events
    val onConnected = Event<Unit>()
    val onDisconnected = Event<Unit>()

    val onBotListUpdate = Event<BotListUpdate>()

    val onGameStarted = Event<GameStartedEvent>()
    val onGameEnded = Event<GameEndedEvent>()
    val onGameAborted = Event<GameAbortedEvent>()

    val onTickEvent = Event<TickEvent>()

    val onBotDeathEvent = Event<BotDeathEvent>()

    var currentGameSetup: GameSetup? = null

    private var websocket: WebSocketClient = WebSocketClient(defaultUri)

    private var clientKey: String? = null
    private var games: Set<GameSetup> = HashSet()
    private var bots: Set<BotInfo> = HashSet()

    private var isGameRunning: Boolean = false

    override fun close() {
        abortGame()

        if (websocket.isOpen()) {
            websocket.close()
        }

        onDisconnected.publish(Unit)
    }

    fun connect(uri: URI) {
        websocket = WebSocketClient(uri)

        websocket.onOpen.subscribe { onConnected.publish(Unit) }
        websocket.onClose.subscribe { onDisconnected.publish(Unit) }
        websocket.onMessage.subscribe { onMessage(it) }

        websocket.open() // must be called after onOpen.subscribe()
    }

    fun isConnected() = websocket.isOpen()

    fun getAvailableBots(): Set<BotInfo> = bots

    fun startGame(gameSetup: GameSetup, botAddresses: Set<BotAddress>) {
        if (!isGameRunning && websocket.isOpen()) {
            websocket.send(StartGame(clientKey!!, gameSetup, botAddresses))
        }
    }

    fun abortGame() {
        if (isGameRunning && websocket.isOpen()) {
            websocket.send(StopGame(clientKey!!))
        }
    }

    private fun onMessage(msg: String) {
        when (val content = Klaxon().parse<Message>(msg)) {
            is ServerHandshake -> handleServerHandshake(content)
            is BotListUpdate -> handleBotListUpdate(content)
            is GameStartedEvent -> handleGameStarted(content)
            is GameEndedEvent -> handleGameEnded(content)
            is GameAbortedEvent -> handleGameAborted(content)
            is TickEvent -> handleTickEvent(content)
            else -> throw IllegalArgumentException("Unknown content type: $content")
        }
    }

    private fun handleServerHandshake(serverHandshake: ServerHandshake) {
        clientKey = serverHandshake.clientKey
        games = serverHandshake.games

        val handshake = ControllerHandshake(
                clientKey = this.clientKey ?: throw IllegalStateException("client key cannot be null"),
                name = "Robocode 2 UI",
                version = "0.1",
                author = "Flemming N. Larsen"
        )
        websocket.send(handshake)
    }

    private fun handleBotListUpdate(botListUpdate: BotListUpdate) {
        bots = botListUpdate.bots
        onBotListUpdate.publish(botListUpdate)
    }

    private fun handleGameStarted(gameStartedEvent: GameStartedEvent) {
        isGameRunning = true
        currentGameSetup = gameStartedEvent.gameSetup

        onGameStarted.publish(gameStartedEvent)
    }

    private fun handleGameEnded(gameEndedEvent: GameEndedEvent) {
        isGameRunning = false
        onGameEnded.publish(gameEndedEvent)
    }

    private fun handleGameAborted(gameAbortedEvent: GameAbortedEvent) {
        isGameRunning = false
        onGameAborted.publish(gameAbortedEvent)
    }

    private fun handleTickEvent(tickEvent: TickEvent) {
        onTickEvent.publish(tickEvent)
    }
}