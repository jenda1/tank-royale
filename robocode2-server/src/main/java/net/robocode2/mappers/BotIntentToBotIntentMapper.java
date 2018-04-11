package net.robocode2.mappers;

import net.robocode2.model.BotIntent;

public final class BotIntentToBotIntentMapper {

	private BotIntentToBotIntentMapper() {}

	public static BotIntent map(net.robocode2.json_schema.comm.BotIntent intent) {

		return BotIntent.builder()
			.targetSpeed(intent.getTargetSpeed())
			.turnRate(intent.getTurnRate())
			.gunTurnRate(intent.getGunTurnRate())
			.radarTurnRate(intent.getRadarTurnRate())
			.bulletPower(intent.getBulletPower())
			.build();
	}
}