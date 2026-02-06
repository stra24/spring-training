package com.example.springtraining.domain.dto;

import com.example.springtraining.domain.entity.Tag;
import lombok.Value;

@Value
public class TagDto {

  Long id;
  String name;

  public static TagDto from(Tag tag) {
    return new TagDto(
        tag.getId(),
        tag.getName()
    );
  }
}