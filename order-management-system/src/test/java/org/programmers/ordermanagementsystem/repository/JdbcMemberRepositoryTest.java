package org.programmers.ordermanagementsystem.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.programmers.ordermanagementsystem.domain.Address;
import org.programmers.ordermanagementsystem.domain.Age;
import org.programmers.ordermanagementsystem.domain.Email;
import org.programmers.ordermanagementsystem.domain.Member;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcMemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private final JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            template.update("DELETE from member");
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
        MemberRepository memberRepository() {
            return new JdbcMemberRepository(dataSource());
        }
    }

    private static Member member1;
    private static Member member2;
    private static Member member3;

    @BeforeEach
    void initializeData() {
        member1 = memberRepository.save(new MemberCreateForm(
                "테스트 회원1", LocalDate.of(1985, 7, 21), "tester@gmail.com", "13494",
                "경기 성남시 분당구 판교역로 235", "경기 성남시 분당구 삼평동 681", "", "(삼평동)"
        ));
        member2 = memberRepository.save(new MemberCreateForm("테스트 회원2", LocalDate.of(2002, 5, 18), "testman2@naver.com", "05071",
                "서울 광진구 아차산로 186-2", "서울 광진구 자양동 23-9", "", "(자양동)"));
        member3 = memberRepository.save(new MemberCreateForm("테스트 회원3", LocalDate.of(1997, 3, 6), "tester3@gmail.com", "25514",
                "강원 강릉시 교동광장로 138-12", "강원 강릉시 교동 1766", "303호", " (교동, 교동주공3단지아파트)"));
    }

    @AfterEach
    void cleanTestData() {
        dataSourceCleaner.cleanDataBase();
    }

    @Test
    @DisplayName("갱신된 회원 데이터가 제대로 조회되어야 한다.")
    void updateAndFind() {
        var memberToBeUpdated = new Member(member1.getId(), "변경된 테스트 회원명",
                new Age(LocalDate.of(1985, 7, 21)),
                new Email("tester15@gmail.com"), new Address("63309",
                "제주특별자치도 제주시 첨단로 242", "제주특별자치도 제주시 영평동 2181",
                "501호", "(영평동)"
        ));
        memberRepository.update(memberToBeUpdated);
        var memberAfterUpdate = memberRepository.findById(member1.getId()).get();
        assertThat(memberAfterUpdate).isEqualTo(memberToBeUpdated);
    }

    @Test
    @DisplayName("전체 상품 데이터 수, 내용이 일치해야 한다.")
    void findAll() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.containsAll(List.of(member1, member2, member3)));
        assertThat(members.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품이 정상적으로 삭제되어야 한다.")
    void delete() {
        memberRepository.delete(member1.getId());
        memberRepository.delete(member2.getId());
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
    }
}