package org.programmers.ordermanagementsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;
import org.programmers.ordermanagementsystem.repository.ItemRepository;
import org.programmers.ordermanagementsystem.repository.JdbcItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.programmers.ordermanagementsystem.domain.ItemType.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private final JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            template.update("DELETE from item");
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .addScript("db/schema.sql")
                    .build();
        }

        @Bean
        public DataSourceCleaner dataSourceCleaner() {
            return new DataSourceCleaner(dataSource());
        }

        @Bean
        ItemRepository itemRepository() {
            return new JdbcItemRepository(dataSource());
        }
    }

    private static Item item1;
    private static Item item2;
    private static Item item3;

    @BeforeEach
    void initializeData() {
        item1 = itemRepository.save(new ItemCreateForm("신속 배송 커피", 3000, 5000, COFFEE));
        item2 = itemRepository.save(new ItemCreateForm("노트북", 1000000, 600, COFFEE));
        item3 = itemRepository.save(new ItemCreateForm("미성년자가 못 사는 담배", 1000, 950, CIGARETTE));
    }

    @AfterEach
    void cleanTestData() {
        dataSourceCleaner.cleanDataBase();
    }

    @Test
    @DisplayName("갱신된 상품 데이터가 제대로 조회되어야 한다.")
    void updateAndFind() {
        var itemToBeUpdated = new Item(item1.getId(), "리얼포스 키보드", 300000, 10, ELECTRONICS);
        itemRepository.update(itemToBeUpdated);
        var itemAfterUpdate = itemRepository.findById(item1.getId()).get();
        assertThat(itemAfterUpdate).isEqualTo(itemToBeUpdated);
    }

    @Test
    @DisplayName("전체 상품 데이터 수, 내용이 일치해야 한다.")
    void findAll() {
        List<Item> items = itemRepository.findAll();
        assertThat(items.containsAll(List.of(item1, item2, item3)));
        assertThat(items.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품이 정상적으로 삭제되어야 한다.")
    void delete() {
        itemRepository.delete(item1.getId());
        itemRepository.delete(item2.getId());
        List<Item> items = itemRepository.findAll();
        assertThat(items.size()).isEqualTo(1);
    }
}