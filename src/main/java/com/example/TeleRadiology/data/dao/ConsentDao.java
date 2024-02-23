package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ConsentEntity;

public interface ConsentDao extends JpaRepository<ConsentEntity, Integer> {

}
