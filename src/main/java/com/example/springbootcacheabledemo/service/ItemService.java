package com.example.springbootcacheabledemo.service;

import com.example.springbootcacheabledemo.model.Item;
import com.example.springbootcacheabledemo.repository.ItemRepository;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Cacheable(value = "itemCache")
    public Item getItemForId(String id) {
        return itemRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Cacheable(value = "randomNumbersCache")
    public int getRandomNumber() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return random.nextInt();
    }

    @Cacheable(value = "complexKeyCache", key = "{ #keyPart1, #keyPart2 }")
    public String getComplexKey(String keyPart1, int keyPart2, String notKey) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return keyPart1 + keyPart2;
    }
}
