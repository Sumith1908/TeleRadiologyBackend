package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.RadiologistEntity;

public interface RadiologistDao extends JpaRepository<RadiologistEntity, Integer> {

}
