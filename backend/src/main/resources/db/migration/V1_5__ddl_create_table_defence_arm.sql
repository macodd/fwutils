CREATE TABLE defence_arm  (
	id BIGINT NOT NULL PRIMARY KEY ,
	create_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	update_time TIMESTAMP WITH TIME ZONE NULL,
	row_version BIGINT NOT NULL DEFAULT 0,
	url VARCHAR(100) NOT NULL,
	name VARCHAR(100) NOT NULL,
	strength INTEGER NULL,
	is_nondurable BOOLEAN NULL,
	required_power INTEGER NULL,
	required_intelligence INTEGER NULL,
	required_courses INTEGER NULL,	
	CONSTRAINT defence_arm_un UNIQUE (url)
);

CREATE SEQUENCE defence_arm_seq MAXVALUE 9223372036854775807 NO CYCLE;