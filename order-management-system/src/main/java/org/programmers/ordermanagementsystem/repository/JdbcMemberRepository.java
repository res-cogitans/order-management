package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.Address;
import org.programmers.ordermanagementsystem.domain.Age;
import org.programmers.ordermanagementsystem.domain.Email;
import org.programmers.ordermanagementsystem.domain.Member;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;
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

import static java.util.Collections.singletonMap;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcMemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member save(MemberCreateForm args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updatedRow = template.update("INSERT INTO member(name, birth_date, email, postcode, road_address," +
                        " lot_number_address, detail_address, extra_address) VALUES(:name, :birthDate, :email, :postcode," +
                        " :roadAddress, :lotNumberAddress, :detailAddress, :extraAddress)",
                toParamMap(args, new MapSqlParameterSource()), keyHolder);
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        Long id = keyHolder.getKey().longValue();

        return new Member(id, args.getName(), new Age(args.getBirthDate()), new Email(args.getEmail()), new Address(
                args.getPostcode(), args.getRoadAddress(), args.getLotNumberAddress(), args.getDetailAddress(), args.getExtraAddress()));
    }

    private SqlParameterSource toParamMap(Member member) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", member.getId());
        return toParamMap(new MemberCreateForm(member.getName(), member.getAge().getBirthDate(), member.getEmail().getAddress(),
                member.getAddress().getPostCode(), member.getAddress().getRoadAddress(), member.getAddress().getLotNumberAddress(),
                member.getAddress().getDetailAddress(), member.getAddress().getExtraAddress()), paramMap);
    }

    private SqlParameterSource toParamMap(MemberCreateForm args, MapSqlParameterSource paramMap) {
        paramMap.addValue("name", args.getName());
        paramMap.addValue("birthDate", args.getBirthDate());
        paramMap.addValue("email", args.getEmail());
        paramMap.addValue("postcode", args.getPostcode());
        paramMap.addValue("roadAddress", args.getRoadAddress());
        paramMap.addValue("lotNumberAddress", args.getLotNumberAddress());
        paramMap.addValue("detailAddress", args.getDetailAddress());
        paramMap.addValue("extraAddress", args.getExtraAddress());
        return paramMap;
    }

    @Override
    public Member update(Member member) {
        int updatedRow = template.update("UPDATE member SET name = :name, birth_date = :birthDate, email = :email," +
                " postcode = :postcode, road_address = :roadAddress, lot_number_address = :lotNumberAddress," +
                " detail_address = :detailAddress, extra_address = :extraAddress WHERE id = :id", toParamMap(member));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        try {
            return Optional.of(template.queryForObject(
                    "SELECT * FROM member WHERE id = :id", singletonMap("id", id), rowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        return template.query("SELECT * FROM member", rowMapper());
    }

    private RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            var age = new Age(rs.getDate("birth_date").toLocalDate());
            var email = new Email(rs.getString("email"));
            var address = new Address(rs.getString("postcode"), rs.getString("road_address"),
                    rs.getString("lot_number_address"), rs.getString("detail_address"),
                    rs.getString("extra_address"));
            return new Member(id, name, age, email, address);
        };
    }

    @Override
    public void delete(Long id) {
        int updatedRow = template.update("DELETE FROM member WHERE id = :id", singletonMap("id", id));
        if (updatedRow != 1) {
            throw new IncorrectResultSizeDataAccessException(1, updatedRow);
        }
    }
}
