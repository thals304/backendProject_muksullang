CREATE DATABASE MUKSULLANG_RRS;
USE MUKSULLANG_RRS;

CREATE TABLE MEMBER(
	MEMBER_ID               VARCHAR(100) PRIMARY KEY,
	PASSWD                  VARCHAR(100) NOT NULL,
	EMAIL                   VARCHAR(50)  NOT NULL,
	ADDRESS                 VARCHAR(25)  NOT NULL,
	PROFILE_ORIGINAL_NAME   VARCHAR(200) DEFAULT 'userProfile.PNG',
	PROFILE_UUID            VARCHAR(200) DEFAULT 'userProfile.PNG',
	AGREE_YN       			CHAR(1)      DEFAULT 'y',
	AGREE_EMAIL_YN 			CHAR(1),
	ACTIVE_YN      			CHAR(1)      DEFAULT 'y',
	INACTIVE_AT    			TIMESTAMP    DEFAULT NULL,
	JOIN_AT        			TIMESTAMP    DEFAULT (CURRENT_DATE)
);