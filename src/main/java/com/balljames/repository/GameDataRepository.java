package com.balljames.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balljames.model.GameData;

public interface GameDataRepository extends JpaRepository<GameData, Integer> {
	
	//TODO: by playername or teamname
}
