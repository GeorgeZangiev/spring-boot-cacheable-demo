package com.example.springbootcacheabledemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.springbootcacheabledemo.config.CacheConfig;
import com.example.springbootcacheabledemo.model.Item;
import com.example.springbootcacheabledemo.repository.ItemRepository;
import com.example.springbootcacheabledemo.service.ItemService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({CacheConfig.class, ItemService.class})
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableCaching
class ItemServiceCachingIntegrationTest {

    private static final String AN_ID = "id-1";
    private static final String A_DESCRIPTION = "an item";

    @MockBean
    private ItemRepository mockItemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        Item anItem = new Item(AN_ID, A_DESCRIPTION);
        given(mockItemRepository.findById(AN_ID))
                .willReturn(Optional.of(anItem));

        Item itemCacheMiss = itemService.getItemForId(AN_ID);
        Item itemCacheHit = itemService.getItemForId(AN_ID);

        assertThat(itemCacheMiss).isEqualTo(anItem);
        assertThat(itemCacheHit).isEqualTo(anItem);

        verify(mockItemRepository, times(1)).findById(AN_ID);
        assertThat(itemFromCache()).isEqualTo(anItem);
    }

    private Object itemFromCache() {
        return cacheManager.getCache("itemCache").get(AN_ID).get();
    }
}