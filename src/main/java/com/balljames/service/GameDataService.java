package com.balljames.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balljames.model.GameData;
import com.balljames.repository.GameDataRepository;

@Service
public class GameDataService {
	
	@Autowired
	private GameDataRepository repository;
	
	public GameData getGameDataById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<GameData> getGameDataChunk(int id, int chunksize){
		// TODO: test last chunk
		List<GameData> data = new ArrayList<GameData>(chunksize);
		for (int i = id; i <= id+chunksize; i++) {
			data.add(repository.findById(i).orElse(null));
		}
		return data;
	}
	
	public List<GameData> getGameData() {
		return repository.findAll();
	}
	
}
