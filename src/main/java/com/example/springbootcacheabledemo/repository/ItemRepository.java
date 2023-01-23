package com.example.springbootcacheabledemo.repository;

import com.example.springbootcacheabledemo.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {
}
