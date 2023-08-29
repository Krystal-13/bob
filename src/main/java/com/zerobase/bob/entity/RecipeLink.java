package com.zerobase.bob.entity;

import javax.persistence.*;

import com.zerobase.bob.entity.type.RecipeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RecipeLink {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String link;
  private String name;

  @Enumerated(EnumType.STRING)
  private RecipeType source;

  public RecipeLink(String link, String name, RecipeType source) {
    this.link = link;
    this.name = name;
    this.source = source;
  }

}
