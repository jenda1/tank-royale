package net.robocode2.model.mappers;

import net.robocode2.json_schema.states.BotState;
import net.robocode2.model.IBot;
import net.robocode2.util.MathUtil;

public final class BotToBotStateMapper {

	public static BotState map(IBot bot) {
		BotState botState = new BotState();
		botState.setEnergy(bot.getEnergy());
		botState.setPosition(PointMapper.map(bot.getPosition()));
		botState.setSpeed(bot.getSpeed());
		botState.setDirection(MathUtil.normalAbsoluteDegrees(bot.getDirection()));
		botState.setRadarDirection(MathUtil.normalAbsoluteDegrees(bot.getRadarDirection()));
		botState.setGunDirection(MathUtil.normalAbsoluteDegrees(bot.getGunDirection()));
		botState.setScanField(ScanFieldMapper.map(bot.getScanField()));
		return botState;
	}
}