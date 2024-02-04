CREATE SEQUENCE parcel_order_seq;
CREATE TABLE parcel_order (
  id                    BIGINT         DEFAULT nextval('parcel_order_seq') PRIMARY KEY,
  uuid                  UUID           NOT NULL UNIQUE,
  author                VARCHAR(50)    NOT NULL,
  description           VARCHAR(500),
  destination           VARCHAR(500)   NOT NULL,
  status                VARCHAR(50)    NOT NULL,
  type                  VARCHAR(50)    NOT NULL,
  courier               VARCHAR(50),
  creation_date         TIMESTAMP      NOT NULL,
  update_date           TIMESTAMP      NOT NULL,
  exp_delivery_date     TIMESTAMP
);

-------------------------------------------------------------

CREATE SEQUENCE watcher_seq;
CREATE TABLE watcher (
  id                    BIGINT         DEFAULT nextval('watcher_seq') PRIMARY KEY,
  order_id              BIGINT         NOT NULL,
  watcher_name          VARCHAR(50)    NOT NULL,
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES parcel_order (id)
);