INSERT INTO item(name, price, stock, type)
VALUES ('맥심 커피', 50000, 5000, 'COFFEE');
INSERT INTO item(name, price, stock, type)
VALUES ('맥북', 2000000, 300, 'ELECTRONICS');
INSERT INTO item(name, price, stock, type)
VALUES ('ESSE', 4500, 9000, 'CIGARETTE');

INSERT INTO member(name, birth_date, email, postcode, road_address, lot_number_address, detail_address, extra_address)
VALUES ('테스트 회원1', '1985-07-21', 'tester@gmail.com', '13494', '경기 성남시 분당구 판교역로 235', '경기 성남시 분당구 삼평동 681', '',
        '(삼평동)');
INSERT INTO member(name, birth_date, email, postcode, road_address, lot_number_address, detail_address, extra_address)
VALUES ('테스트 회원2', '2002-05-18', 'testman2@naver.com', '05071', '서울 광진구 아차산로 186-2', '서울 광진구 자양동 23-9', '',
        '(자양동)');
INSERT INTO member(name, birth_date, email, postcode, road_address, lot_number_address, detail_address, extra_address)
VALUES ('테스트 회원3', '1997-03-06', 'tester3@gmail.com', '25514', '강원 강릉시 교동광장로 138-12', '강원 강릉시 교동 1766', '303호',
        '(교동, 교동주공3단지아파트)');