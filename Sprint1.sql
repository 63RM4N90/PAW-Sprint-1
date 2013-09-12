CREATE TABLE Users (
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
	id		SERIAL NOT NULL UNIQUE,
	hashtag		varchar(139) NOT NULL UNIQUE,
	creator		varchar(32) REFERENCES Users(username),
	date		TIMESTAMP NOT NULL,
	PRIMARY KEY(id, hashtag)
);

CREATE TABLE Comments (
	id		SERIAL UNIQUE NOT NULL,
	username	varchar(32) REFERENCES Users(username) ON DELETE CASCADE,
	date		TIMESTAMP NOT NULL,
	comment		varchar(140),
	PRIMARY KEY(id, username, date, time)
);

CREATE TABLE HashtagsInComments (
	commentId	int REFERENCES Comments(id),
	hashtagId	int NOT NULL REFERENCES Hashtags(id)
);
