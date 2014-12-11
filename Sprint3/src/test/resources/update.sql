ALTER TABLE userss ADD COLUMN background bytea;
ALTER TABLE userss ADD COLUMN picture_extension character varying(255);
ALTER TABLE userss ADD COLUMN token character varying(255);


-- Table: userlist

-- DROP TABLE userlist;

CREATE TABLE userlist
(
  id serial NOT NULL,
  _name character varying(255) NOT NULL,
  _owner_id integer,
  CONSTRAINT userlist_pkey PRIMARY KEY (id),
  CONSTRAINT fkf3f497292a17019f FOREIGN KEY (_owner_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userlist
  OWNER TO paw;


-- Table: userss_blacklists

-- DROP TABLE userss_blacklists;

CREATE TABLE userss_blacklists
(
  blacklisted_by_id integer NOT NULL,
  blacklisted_users_id integer NOT NULL,
  CONSTRAINT fkb4165c4a6c0b9c99 FOREIGN KEY (blacklisted_by_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkb4165c4a74b3d32e FOREIGN KEY (blacklisted_users_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss_blacklists
  OWNER TO paw;


-- Table: userss_userlist

-- DROP TABLE userss_userlist;

CREATE TABLE userss_userlist
(
  _users_id integer NOT NULL,
  lists_id integer NOT NULL,
  CONSTRAINT userss_userlist_pkey PRIMARY KEY (_users_id, lists_id),
  CONSTRAINT fka7ca3d7d8bee8c5c FOREIGN KEY (lists_id)
      REFERENCES userlist (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fka7ca3d7db7212a2a FOREIGN KEY (_users_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss_userlist
  OWNER TO paw;


-- Table: publicity

-- DROP TABLE publicity;

CREATE TABLE publicity
(
  id serial NOT NULL,
  _client_name character varying(255) NOT NULL,
  _image_url character varying(255) NOT NULL,
  _redirection_url character varying(255) NOT NULL,
  CONSTRAINT publicity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE publicity
  OWNER TO paw;
  