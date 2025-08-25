DROP TABLE IF EXISTS memnik.roles;
DROP TABLE IF EXISTS memnik.users;
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-security-tables.xml';

DROP TABLE IF EXISTS memnik.shedlock;
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-shedlock-tables.xml';

DROP TABLE IF EXISTS memnik.tags;
DROP TABLE IF EXISTS memnik.mems;
DROP TABLE IF EXISTS memnik.mems_tags;
DROP TABLE IF EXISTS memnik.jokes;
DROP TABLE IF EXISTS memnik.jokes_tags;
DROP TABLE IF EXISTS memnik.postcards;
DROP TABLE IF EXISTS memnik.postcards_tags;
DROP TABLE IF EXISTS memnik.quotes;
DROP TABLE IF EXISTS memnik.quotes_tags;
DROP TABLE IF EXISTS memnik.videos;
DROP TABLE IF EXISTS memnik.videos_tags;

DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-tag-table.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-insert-tag-table.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-mem-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-insert-mem-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-joke-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-insert-joke-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-postcard-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-insert-postcard-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-quote-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-insert-quote-tag-tables.xml';
DELETE FROM memnik.databasechangelog WHERE filename = '0.0.1/0.0.1-create-video-tag-tables.xml';
