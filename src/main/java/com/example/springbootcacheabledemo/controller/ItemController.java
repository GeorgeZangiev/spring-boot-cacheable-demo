package com.example.springbootcacheabledemo.controller;

import com.example.springbootcacheabledemo.model.Item;
import com.example.springbootcacheabledemo.service.ItemService;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable String id) {
        return itemService.getItemForId(id);
    }

    @GetMapping("/random")
    public int getRandomNumber() throws NoSuchAlgorithmException {
        return itemService.getRandomNumber();
    }

    @GetMapping("/complex-key")
    public String getComplexKey(String keyPart1, int keyPart2, String notKey) {
        return itemService.getComplexKey(keyPart1, keyPart2, notKey);
    }
}
