#!/bin/bash

psql -v ON_ERROR_STOP=1 -U ${POSTGRES_USER} -d "postgres" <<-EOSQL
    SELECT 'CREATE DATABASE $POSTGRES_DB WITH OWNER $POSTGRES_USER' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$POSTGRES_DB');
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$POSTGRES_DB" <<-EOSQL
CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION root;

-- create user sequence
CREATE SEQUENCE IF NOT EXISTS public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- create task sequence
CREATE SEQUENCE IF NOT EXISTS public.task_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- create a table users
CREATE TABLE IF NOT EXISTS "user"(
  id INT4 DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
  firstname VARCHAR NOT NULL,
  lastname VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  CONSTRAINT user_email_key UNIQUE (email),
  CONSTRAINT user_pkey PRIMARY KEY (id)
);

-- create a table tasks
CREATE TABLE IF NOT EXISTS task(
  id INT4 DEFAULT nextval('task_id_seq'::regclass) NOT NULL,
  title VARCHAR NOT NULL,
  status INT4 default 0 not NULL,
  is_active bool DEFAULT true NOT NULL,
  date timestamptz NOT NULL,
  user_id integer REFERENCES "user"(id) ON DELETE cascade,
  CONSTRAINT task_pkey PRIMARY KEY (id)
);
EOSQL

# Prepopulate tables if necessary, comment this section out if not required
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$POSTGRES_DB" <<-EOSQL
---- add test data
    INSERT INTO "user" (firstname, lastname, email) VALUES ('adam', 'adrian', 'adam@example.com');
    INSERT INTO "user" (firstname, lastname, email) VALUES ('dan', 'blake', 'dan@example.com');
    INSERT INTO "user" (firstname, lastname, email) VALUES ('colin', 'allan', 'colin@example.com');
    INSERT INTO "user" (firstname, lastname, email) VALUES ('emma', 'davies', 'emma@example.com');
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('clean the windows', 0, true, '2024-01-23 17:13:47', 1);
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('take the litter out', 0, true, '2024-01-25 17:13:47', 1);
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('mop the kitchen''s floor', 0, true, '2024-01-26 17:13:47', 1);
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('chop the onions', 0, true, '2024-03-23 17:10:47', 3);
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('peel the potatoes', 0, true, '2024-03-22 10:10:47', 3);
    INSERT INTO task (title, status, is_active, date, user_id) VALUES ('mown the lawn', 0, true, '2024-03-22 10:10:47', 2);
EOSQL
