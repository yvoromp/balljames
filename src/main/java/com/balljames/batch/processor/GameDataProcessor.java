package com.balljames.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.balljames.model.GameData;

public class GameDataProcessor implements ItemProcessor<GameData, GameData>{

	@Override
	public GameData process(GameData gamedata) throws Exception {
		return gamedata;
	}

}
