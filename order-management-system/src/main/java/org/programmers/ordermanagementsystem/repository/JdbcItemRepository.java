package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.domain.ItemType;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;

@Repository
public class JdbcItemRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcItemRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(ItemCreateForm args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updatedRow = template.update("INSERT INTO item(name, price, stock, type) VALUES(:name, :price, :stock, :type)",
                toParamMap(args, new MapSqlParameterSource()), keyHolder);
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        Long id = keyHolder.getKey().longValue();

        return new Item(id, args.getName(), args.getPrice(), args.getStock(), args.getType());
    }

    private SqlParameterSource toParamMap(Item item) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", item.getId());
        return toParamMap(new ItemCreateForm(item.getName(), item.getPrice(), item.getStock(), item.getType()), paramMap);
    }

    private SqlParameterSource toParamMap(ItemCreateForm args, MapSqlParameterSource paramMap) {
        paramMap.addValue("name", args.getName());
        paramMap.addValue("price", args.getPrice());
        paramMap.addValue("stock", args.getStock());
        paramMap.addValue("type", args.getType().toString());
        return paramMap;
    }

    @Override
    public Item update(Item item) {
        int updatedRow = template.update("UPDATE item SET name = :name, price = :price, stock = :stock, type = :type WHERE id = :id", toParamMap(item));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            return Optional.of(template.queryForObject(
                    "SELECT * FROM item WHERE id = :id", singletonMap("id", id), rowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll() {
        return template.query("SELECT * FROM item", rowMapper());
    }

    private RowMapper<Item> rowMapper() {
        return (rs, rowNum) -> {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            var price = rs.getInt("price");
            var stock = rs.getInt("stock");
            var type = ItemType.valueOf(rs.getString("type"));
            return new Item(id, name, price, stock, type);
        };
    }

    @Override
    public void delete(Long id) {
        int updatedRow = template.update("DELETE FROM item WHERE id = :id", singletonMap("id", id));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
    }
}