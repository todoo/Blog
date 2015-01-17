create table User(
	userID integer not null AUTO_INCREMENT,
	urlID varchar(100) not null,
	userName varchar(100) not null,
	password varchar(100) not null,
	nickName varchar(20),
	logo varchar(100),
	indexAudio varchar(100),
	primary key(userID),
	unique(userName),
	unique(urlID)
);

create table Blog(
	blogID integer not null AUTO_INCREMENT,
	userID integer not null,
	title varchar(100),
	message varchar(500),
	primary key(blogID),
	unique(userID)
);

create table LifeStatus(
	lifeStatusID integer not null AUTO_INCREMENT,
	userID integer not null,
	status varchar(500),
	createTime datetime,
	primary key(lifeStatusID)
);

create table StatisticActivity(
	statActivityID integer not null AUTO_INCREMENT,
	userID integer not null,
	statDate date not null,
	articleCount int not null,
	primary key(statActivityID)
);

create table ArticleCategory(
	categoryID integer not null AUTO_INCREMENT,
	userID integer not null,
	parentID integer not null default 0,
	categoryName varchar(10) not null,
	primary key(categoryID)
);

create table Article(
	articleID integer not null AUTO_INCREMENT,
	userID integer not null,
	categoryID integer not null,
	articleTitle varchar(20) not null,
	articleBrief varchar(500),
	articleContent text not null,
	createTime datetime not null,
	updateTime datetime not null,
	primary key(articleID)
);

create table StatisticClassify(
	statClassifyID integer not null AUTO_INCREMENT,
	userID integer not null,
	categoryID integer not null,
	percent decimal(10,2) not null,
	primary key(statClassifyID),
	unique(categoryID)
);

create table DicMediaType (
	mediaTypeID integer not null AUTO_INCREMENT,
	mediaTypeName varchar(10) not null,
	mediaTypeDisplayName varchar(20) not null,
	primary key(mediaTypeID)
);

create table Media(
	mediaID integer not null AUTO_INCREMENT,
	userID integer not null,
	mediaTypeID integer not null,
	mediaPath varchar(100) not null,
	primary key(mediaID)
);

create table BackgroundMusic(
	bgMusicID integer not null AUTO_INCREMENT,
	userID integer not null,
	mediaID integer not null,
	bgMusicName varchar(20) not null,
	primary key(bgMusicID),
	unique(mediaID)
);