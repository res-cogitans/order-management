DROP SEQUENCE IF EXISTS ITEM_SEQ;
DROP SEQUENCE IF EXISTS MEMBER_SEQ;
DROP SEQUENCE IF EXISTS ORDER_SEQ;
DROP SEQUENCE IF EXISTS ORDER_ITEM_SEQ;

DROP TABLE IF EXISTS ITEM;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS ORDER_ITEM;

CREATE SEQUENCE ITEM_SEQ;
CREATE SEQUENCE MEMBER_SEQ;
CREATE SEQUENCE ORDER_SEQ;
CREATE SEQUENCE ORDER_ITEM_SEQ;

CREATE TABLE ITEM
(
    id    BIGINT DEFAULT ITEM_SEQ.nextval PRIMARY KEY,
    name  VARCHAR(20) NOT NULL,
    price INT         NOT NULL,
    stock INT    DEFAULT 0,
    type  VARCHAR(20) NOT NULL
);

CREATE TABLE MEMBER
(
    id                 BIGINT DEFAULT MEMBER_SEQ.nextval PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    birth_date         DATE        NOT NULL,
    email              VARCHAR(40) NOT NULL UNIQUE,
    postcode           VARCHAR(50) NOT NULL,
    road_address       VARCHAR(50) NOT NULL,
    lot_number_address VARCHAR(50) NOT NULL,
    detail_address     VARCHAR(50),
    extra_address      VARCHAR(50) NOT NULL
);

CREATE TABLE ORDERS
(
    id                 BIGINT DEFAULT ORDER_SEQ.nextval PRIMARY KEY,
    total_price        INTEGER     NOT NULL,
    postcode           VARCHAR(50) NOT NULL,
    road_address       VARCHAR(50) NOT NULL,
    lot_number_address VARCHAR(50) NOT NULL,
    detail_address     VARCHAR(50),
    extra_address      VARCHAR(50) NOT NULL,
    order_status       VARCHAR(20) NOT NULL,
    created_at         DATETIME(6) NOT NULL,
    member_id          BIGINT      NOT NULL,
    FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

CREATE TABLE ORDER_ITEM
(
    id       BIGINT DEFAULT ORDER_ITEM_SEQ.nextval PRIMARY KEY,
    order_id BIGINT NOT NULL,
    item_id  BIGINT NOT NULL,
    quantity INT    NOT NULL,
    FOREIGN KEY (order_id) REFERENCES ORDERS (id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES ITEM (id)
);