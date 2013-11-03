-- Table: userss

-- DROP TABLE userss;

CREATE TABLE userss
(
  id serial NOT NULL,
  description character varying(255),
  isprivate boolean NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  picture bytea,
  registrationdate timestamp without time zone NOT NULL,
  secretanswer character varying(255) NOT NULL,
  secretquestion character varying(255) NOT NULL,
  surname character varying(255) NOT NULL,
  username character varying(255) NOT NULL,
  visits integer NOT NULL,
  CONSTRAINT userss_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss
  OWNER TO paw;

-- Table: comment

-- DROP TABLE comment;

CREATE TABLE comment
(
  id serial NOT NULL,
  comment character varying(255) NOT NULL,
  date timestamp without time zone NOT NULL,
  author_id integer,
  originalauthor_id integer,
  CONSTRAINT comment_pkey PRIMARY KEY (id),
  CONSTRAINT fk9bde863f19841268 FOREIGN KEY (author_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9bde863fdd1103b7 FOREIGN KEY (originalauthor_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comment
  OWNER TO paw;

-- Table: hashtag

-- DROP TABLE hashtag;

CREATE TABLE hashtag
(
  id serial NOT NULL,
  date timestamp without time zone NOT NULL,
  hashtag character varying(255),
  author_id integer,
  CONSTRAINT hashtag_pkey PRIMARY KEY (id),
  CONSTRAINT fk8ccc53ac19841268 FOREIGN KEY (author_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hashtag
  OWNER TO paw;

-- Table: notification

-- DROP TABLE notification;

CREATE TABLE notification
(
  id serial NOT NULL,
  checked boolean NOT NULL,
  date timestamp without time zone,
  notification character varying(255),
  notificator_id integer,
  CONSTRAINT notification_pkey PRIMARY KEY (id),
  CONSTRAINT fk2d45dd0bf0e1eb33 FOREIGN KEY (notificator_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE notification
  OWNER TO paw;

-- Table: comment_hashtag

-- DROP TABLE comment_hashtag;

CREATE TABLE comment_hashtag
(
  comments_id integer NOT NULL,
  hashtags_id integer NOT NULL,
  CONSTRAINT comment_hashtag_pkey PRIMARY KEY (comments_id, hashtags_id),
  CONSTRAINT fk462bd1acdc3ed8f1 FOREIGN KEY (hashtags_id)
      REFERENCES hashtag (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk462bd1acea11bc97 FOREIGN KEY (comments_id)
      REFERENCES comment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comment_hashtag
  OWNER TO paw;

-- Table: comment_userss

-- DROP TABLE comment_userss;

CREATE TABLE comment_userss
(
  comment_id integer NOT NULL,
  references_id integer NOT NULL,
  CONSTRAINT comment_userss_pkey PRIMARY KEY (comment_id, references_id),
  CONSTRAINT fkcbe3f56b210a8c4b FOREIGN KEY (references_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkcbe3f56ba23a504c FOREIGN KEY (comment_id)
      REFERENCES comment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comment_userss
  OWNER TO paw;

-- Table: userss_comment

-- DROP TABLE userss_comment;

CREATE TABLE userss_comment
(
  favouritees_id integer NOT NULL,
  favourites_id integer NOT NULL,
  CONSTRAINT userss_comment_pkey PRIMARY KEY (favouritees_id, favourites_id),
  CONSTRAINT fk3650c56b2c76915c FOREIGN KEY (favouritees_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk3650c56b701b39e1 FOREIGN KEY (favourites_id)
      REFERENCES comment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss_comment
  OWNER TO paw;

-- Table: userss_notification

-- DROP TABLE userss_notification;

CREATE TABLE userss_notification
(
  userss_id integer NOT NULL,
  notifications_id integer NOT NULL,
  CONSTRAINT fk1678395fbf23750b FOREIGN KEY (notifications_id)
      REFERENCES notification (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk1678395fd6e63488 FOREIGN KEY (userss_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userss_notification_notifications_id_key UNIQUE (notifications_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss_notification
  OWNER TO paw;

-- Table: userss_userss

-- DROP TABLE userss_userss;

CREATE TABLE userss_userss
(
  followers_id integer NOT NULL,
  following_id integer NOT NULL,
  CONSTRAINT userss_userss_pkey PRIMARY KEY (followers_id, following_id),
  CONSTRAINT fkb21e5ebf51d9cb9e FOREIGN KEY (followers_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkb21e5ebf586f5ba2 FOREIGN KEY (following_id)
      REFERENCES userss (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userss_userss
  OWNER TO paw;