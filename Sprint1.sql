﻿CREATE TABLE Users (
	id		SERIAL NOT NULL,
	name		varchar(32),
	surname		varchar(32),
	password	varchar(16) CHECK (char_length(password) >= 8 AND char_length(password) <= 16) NOT NULL,
	username	varchar(32) NOT NULL UNIQUE,
	description	varchar(140),
	secretQuestion	varchar(64),
	secretAnswer	varchar(64),
	PRIMARY KEY(id)
);

CREATE TABLE Hashtags (
	id		SERIAL NOT NULL,
	hashtag		varchar(139) NOT NULL UNIQUE,
	creator		varchar(32) REFERENCES Users(username),
	date		date NOT NULL,
	time		time NOT NULL,
	PRIMARY KEY(id, hashtag)
);

CREATE TABLE Comments (
	id		SERIAL UNIQUE NOT NULL,
	username	varchar(32) REFERENCES Users(username) ON DELETE CASCADE,
	date		date NOT NULL,
	time		time NOT NULL,
	comment		varchar(140),
	PRIMARY KEY(id, username, date, time)
);

CREATE TABLE HashtagsInComments (
	id		SERIAL NOT NULL,
	commentId	int REFERENCES Comments(id),
	hashtag		varchar(139) REFERENCES Hashtags(hashtag)
);