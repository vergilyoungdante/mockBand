package com.example.mockband.repository;

import com.example.mockband.entity.Windows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindowsRepository extends JpaRepository<Windows,Integer> {
    public Windows findWindowsByAuth(int auth);
}
