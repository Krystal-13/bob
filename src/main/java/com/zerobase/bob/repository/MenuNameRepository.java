package com.zerobase.bob.repository;

import com.zerobase.bob.entity.MenuName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuNameRepository extends JpaRepository<MenuName, String> {
}
