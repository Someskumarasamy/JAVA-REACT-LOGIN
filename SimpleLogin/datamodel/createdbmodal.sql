CREATE TABLE approles (
	role_id serial PRIMARY KEY,
	role_common_name VARCHAR ( 255 ) UNIQUE NOT NULL
);
CREATE TABLE appuser (
	user_id serial PRIMARY KEY,
	email VARCHAR ( 255 ) UNIQUE INDEX NOT NULL
);
CREATE TABLE userrolemapping (
	user_id BIGINT,
	role_id BIGINT,
	PRIMARY KEY(user_id),
	CONSTRAINT fk_user
	  FOREIGN KEY(user_id) 
	  REFERENCES appuser(user_id)
	  ON DELETE CASCADE,
	CONSTRAINT fk_role
	  FOREIGN KEY(role_id) 
	  REFERENCES approles(role_id)
	  ON DELETE CASCADE
	 
	
);