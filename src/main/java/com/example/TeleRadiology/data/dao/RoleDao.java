package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.RoleEntity;

public interface RoleDao extends JpaRepository<RoleEntity, Integer> {

}
