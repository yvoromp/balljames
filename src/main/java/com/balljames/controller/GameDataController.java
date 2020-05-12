package com.balljames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.balljames.model.GameData;
import com.balljames.service.GameDataService;

@RestController
public class GameDataController {
	
	@Autowired
	private GameDataService service;
	
	@GetMapping("/gamedata/{id}")
	public GameData findGameDataById(@PathVariable int id) {
		return service.getGameDataById(id);
	}
	
	@GetMapping("/gamedata/{id}/{chunksize}")
	public List<GameData> findGameDataById(@PathVariable int id, @PathVariable int chunksize) {
		return service.getGameDataChunk(id, chunksize);
	}
	
	@GetMapping("/gamedata")
	public List<GameData> findAllGameData() {
		return service.getGameData();
	}


}
