DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS global_seq2;

CREATE SEQUENCE global_seq START WITH 100000;
CREATE SEQUENCE global_seq2 START WITH 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table meals
(
  id INTEGER PRIMARY KEY DEFAULT nextval('global_seq2'),
  user_id INTEGER not null,
  dateTime TIMESTAMP WITHOUT TIME ZONE Not null,
  description varchar not null,
  calories INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE cascade
);
create UNIQUE INDEX meals_unique_datetime ON meals (dateTime, user_id);
create index id_of_user on meals (user_id);