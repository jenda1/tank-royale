package net.robocode2.mappers;

import java.util.HashSet;
import java.util.Set;

import net.robocode2.json_schema.GameSetup;
import net.robocode2.model.GameSetup.GameSetupBuilder;

public final class GameSetupToGameSetupMapper {

	private GameSetupToGameSetupMapper() {}

	public static GameSetup map(net.robocode2.model.GameSetup gameSetup) {
		GameSetup setup = new GameSetup();

		setup.setGameType(gameSetup.getGameType());
		setup.setArenaWidth(gameSetup.getArenaWidth());
		setup.setArenaHeight(gameSetup.getArenaHeight());
		setup.setMinNumberOfParticipants(gameSetup.getMinNumberOfParticipants());
		setup.setMaxNumberOfParticipants(gameSetup.getMaxNumberOfParticipants());
		setup.setNumberOfRounds(gameSetup.getNumberOfRounds());
		setup.setGunCoolingRate(gameSetup.getGunCoolingRate());
		setup.setInactivityTurns(gameSetup.getInactivityTurns());
		setup.setTurnTimeout(gameSetup.getTurnTimeout());
		setup.setReadyTimeout(gameSetup.getReadyTimeout());
		setup.setDelayedObserverTurns(gameSetup.getDelayedObserverTurns());

		setup.setIsArenaWidthLocked(gameSetup.isArenaWidthLocked());
		setup.setIsArenaHeightLocked(gameSetup.isArenaHeightLocked());
		setup.setIsMinNumberOfParticipantsLocked(gameSetup.isMinNumberOfParticipantsLocked());
		setup.setIsMaxNumberOfParticipantsLocked(gameSetup.isMaxNumberOfParticipantsLocked());
		setup.setIsNumberOfRoundsLocked(gameSetup.isNumberOfRoundsLocked());
		setup.setIsGunCoolingRateLocked(gameSetup.isGunCoolingRateLocked());
		setup.setIsInactivityTurnsLocked(gameSetup.isInactivityTurnsLocked());
		setup.setIsTurnTimeoutLocked(gameSetup.isTurnTimeoutLocked());
		setup.setIsReadyTimeoutLocked(gameSetup.isReadyTimeoutLocked());
		setup.setIsDelayedObserverTurnsLocked(gameSetup.isDelayedObserverTurnsLocked());

		return setup;
	}

	public static net.robocode2.model.GameSetup map(GameSetup gameSetup) {
		GameSetupBuilder builder = net.robocode2.model.GameSetup.builder();

		if (gameSetup.getGameType() != null) {
			builder.gameType(gameSetup.getGameType());
		}
		if (gameSetup.getArenaWidth() != null) {
			builder.arenaWidth(gameSetup.getArenaWidth());
		}
		if (gameSetup.getArenaHeight() != null) {
			builder.arenaHeight(gameSetup.getArenaHeight());
		}
		if (gameSetup.getMinNumberOfParticipants() != null) {
			builder.minNumberOfParticipants(gameSetup.getMinNumberOfParticipants());
		}
		if (gameSetup.getMaxNumberOfParticipants() != null) {
			builder.maxNumberOfParticipants(gameSetup.getMaxNumberOfParticipants());
		}
		if (gameSetup.getNumberOfRounds() != null) {
			builder.numberOfRounds(gameSetup.getNumberOfRounds());
		}
		if (gameSetup.getGunCoolingRate() != null) {
			builder.gunCoolingRate(gameSetup.getGunCoolingRate());
		}
		if (gameSetup.getInactivityTurns() != null) {
			builder.inactivityTurns(gameSetup.getInactivityTurns());
		}
		if (gameSetup.getTurnTimeout() != null) {
			builder.turnTimeout(gameSetup.getTurnTimeout());
		}
		if (gameSetup.getReadyTimeout() != null) {
			builder.readyTimeout(gameSetup.getReadyTimeout());
		}
		if (gameSetup.getDelayedObserverTurns() != null) {
			builder.delayedObserverTurns(gameSetup.getDelayedObserverTurns());
		}
		return builder.build();
	}

	public static Set<GameSetup> map(Set<net.robocode2.model.GameSetup> games) {
		Set<GameSetup> mappedGames = new HashSet<>();
		for (net.robocode2.model.GameSetup gameSetup : games) {
			mappedGames.add(map(gameSetup));
		}
		return mappedGames;
	}
}