package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.*;
import org.programmers.ordermanagementsystem.dto.OrderCreateArgs;
import org.programmers.ordermanagementsystem.dto.OrderItemCreateForm;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonMap;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcOrderRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Order save(OrderCreateArgs args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updatedRow = template.update("INSERT INTO orders(total_price, postcode, road_address, lot_number_address," +
                        " detail_address, extra_address, order_status, created_at, member_id) VALUES(:totalPrice, :postcode," +
                        " :roadAddress, :lotNumberAddress, :detailAddress, :extraAddress, :orderStatus, :createdAt, :memberId)",
                toParamMap(args, new MapSqlParameterSource()), keyHolder);
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        Long orderId = keyHolder.getKey().longValue();

        List<OrderItemCreateForm> orderItemCreateForms = args.getOrderItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemCreateForm orderItemCreateForm : orderItemCreateForms) {
            KeyHolder orderItemKeyHolder = new GeneratedKeyHolder();
            template.update("INSERT INTO order_item(order_id, item_id, quantity, name) VALUES(:orderId, :itemId, :quantity, :name)",
                    toOrderItemParamMap(orderItemCreateForm, orderId), orderItemKeyHolder);
            orderItems.add(new OrderItem(orderItemKeyHolder.getKey().longValue(), orderId, orderItemCreateForm.itemId(), orderItemCreateForm.quantity(), orderItemCreateForm.name()));
        }

        return new Order(orderId, args.getTotalPrice(), new Address(args.getAddress().getPostcode(), args.getAddress().getRoadAddress(),
                args.getAddress().getLotNumberAddress(), args.getAddress().getDetailAddress(), args.getAddress().getExtraAddress())
                , args.getOrderStatus(), args.getCreatedAt(), args.getMemberId(), orderItems);
    }

    private SqlParameterSource toParamMap(OrderCreateArgs args, MapSqlParameterSource paramMap) {
        paramMap.addValue("totalPrice", args.getTotalPrice());
        paramMap.addValue("postcode", args.getAddress().getPostcode());
        paramMap.addValue("roadAddress", args.getAddress().getRoadAddress());
        paramMap.addValue("lotNumberAddress", args.getAddress().getLotNumberAddress());
        paramMap.addValue("detailAddress", args.getAddress().getDetailAddress());
        paramMap.addValue("extraAddress", args.getAddress().getExtraAddress());
        paramMap.addValue("orderStatus", args.getOrderStatus().toString());
        paramMap.addValue("createdAt", args.getCreatedAt());
        paramMap.addValue("memberId", args.getMemberId());
        return paramMap;
    }

    private SqlParameterSource toOrderItemParamMap(OrderItemCreateForm orderItem, Long orderId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("orderId", orderId);
        paramMap.addValue("itemId", orderItem.itemId());
        paramMap.addValue("quantity", orderItem.quantity());
        paramMap.addValue("name", orderItem.name());
        return paramMap;
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        paramMap.addValue("orderStatus", orderStatus.toString());

        template.update("UPDATE orders SET order_status = :orderStatus WHERE id = :id", paramMap);
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            return Optional.of(template.queryForObject(
                    "SELECT * FROM orders WHERE id = :id", singletonMap("id", id), rowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {
        return template.query("SELECT * FROM orders", rowMapper());
    }

    private RowMapper<Order> rowMapper() {
        return (rs, rowNum) -> {
            var id = rs.getLong("id");
            var totalPrice = rs.getInt("total_price");
            var address = new Address(rs.getString("postcode"), rs.getString("road_address"),
                    rs.getString("lot_number_address"), rs.getString("detail_address"),
                    rs.getString("extra_address"));
            var orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var memberId = rs.getLong("member_id");
            List<OrderItem> orderItems = findOrderItemsOfOrder(id);
            return new Order(id, totalPrice, address, orderStatus, createdAt, memberId, orderItems);
        };
    }

    private List<OrderItem> findOrderItemsOfOrder(Long orderId) {
        return template.query("SELECT * FROM order_item WHERE order_id = :orderId", singletonMap("orderId", orderId), orderItemRowMapper());
    }

    private RowMapper<OrderItem> orderItemRowMapper() {
        return (rs, rowNum) -> {
            var id = rs.getLong("id");
            var orderId = rs.getLong("order_id");
            var itemId = rs.getLong("item_id");
            var quantity = rs.getInt("quantity");
            var name = rs.getString("name");
            return new OrderItem(id, orderId, itemId, quantity, name);
        };
    }
}