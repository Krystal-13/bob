package com.zerobase.bob.repository;

import com.zerobase.bob.entity.MenuLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuLinkRepository extends JpaRepository<MenuLink, Long> {

}
