-- database
DROP DATABASE IF EXISTS teachu;
CREATE DATABASE teachu;
USE teachu;
-- user
CREATE USER IF NOT EXISTS 'dev'@'%' IDENTIFIED WITH caching_sha2_password BY 'dev';
GRANT ALL PRIVILEGES ON *.* TO 'dev'@'%' WITH GRANT OPTION;

-- tables
DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id			     char(36) NOT NULL,
    email            VARCHAR(255),
    password         VARCHAR(255),
    role             VARCHAR(255),
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    birthday         DATE,
    sex              VARCHAR(255),
    language         VARCHAR(255),
    dark_theme       BOOLEAN,
    city             VARCHAR(255),
    postal_code      VARCHAR(255),
    street           VARCHAR(255),
    phone            VARCHAR(255),
    img              char(36),
    last_login       TIMESTAMP,
    creation_date    DATE,
    termination_date DATE,
    notes            VARCHAR(4096),
    active           BOOLEAN,
	username text NOT NULL,
	goodSubject1 int NOT NULL,
	goodSubject2 int NOT NULL,
	goodSubject3 int NOT NULL,
	badSubject1 int NOT NULL,
	badSubject2 int NOT NULL,
	badSubject3 int NOT NULL,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    user_id         char(36),
    access          VARCHAR(255) PRIMARY KEY,
    refresh         VARCHAR(255),
    access_expires  TIMESTAMP,
    refresh_expires TIMESTAMP
);

DROP TABLE IF EXISTS school_class;
CREATE TABLE school_class
(
    id         char(36) PRIMARY KEY,
    name       VARCHAR(255),
    teacher_id char(36)
);

DROP TABLE IF EXISTS school_class_user;
CREATE TABLE school_class_user
(
    school_class_id char(36),
    user_id         char(36)
);

DROP TABLE IF EXISTS subject;
CREATE TABLE subject
(
    id     char(36) PRIMARY KEY,
    name   VARCHAR(255),
    weight FLOAT
);

DROP TABLE IF EXISTS school_class_subject;
CREATE TABLE school_class_subject
(
    id              char(36) PRIMARY KEY,
    school_class_id char(36),
    subject_id      char(36),
    teacher_id      char(36),
    start_date      DATE,
    end_date        DATE,
    note            VARCHAR(4096)
);

DROP TABLE IF EXISTS timetable;
CREATE TABLE timetable
(
    id         char(36) PRIMARY KEY,
    start_time TIME,
    end_time   TIME
);

DROP TABLE IF EXISTS lesson;
CREATE TABLE lesson
(
    id                      char(36) PRIMARY KEY,
    school_class_subject_id char(36),
    timetable_id            char(36),
    weekday                 VARCHAR(255),
    room_id                 char(36)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room
(
    id   char(36) PRIMARY KEY,
    name VARCHAR(255),
    note VARCHAR(4096)
);

DROP TABLE IF EXISTS exam;
CREATE TABLE exam
(
    id                      char(36) PRIMARY KEY,
    school_class_subject_id char(36),
    name                    VARCHAR(100),
    description             VARCHAR(250),
    weight                  FLOAT,
    date                    DATE,
    view_date               DATE
);

DROP TABLE IF EXISTS grade;
CREATE TABLE grade
(
    id         char(36) PRIMARY KEY,
    student_id char(36),
    exam_id    char(36),
    mark       FLOAT,
    note       VARCHAR(4096)
);

DROP TABLE IF EXISTS user_event;
CREATE TABLE user_event
(
    id               char(36) PRIMARY KEY,
    user_id          char(36),
    date_from        TIMESTAMP,
    date_to          TIMESTAMP,
    title            VARCHAR(255),
    description      VARCHAR(4096),
    user_event_state VARCHAR(255),
    user_event_type  VARCHAR(255)
);

DROP TABLE IF EXISTS lesson_event;
CREATE TABLE lesson_event
(
    id                char(36) PRIMARY KEY,
    lesson_id         char(36),
    date              DATE,
    title             VARCHAR(255),
    description       VARCHAR(4096),
    lesson_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS school_class_event;
CREATE TABLE school_class_event
(
    id                      char(36) PRIMARY KEY,
    school_class_id         char(36),
    date_from               DATE,
    date_to                 DATE,
    title                   VARCHAR(255),
    description             VARCHAR(4096),
    school_class_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS school_event;
CREATE TABLE school_event
(
    id                char(36) PRIMARY KEY,
    date_from         DATE,
    date_to           DATE,
    title             VARCHAR(255),
    description       VARCHAR(4096),
    school_event_type VARCHAR(255)
);

DROP TABLE IF EXISTS parent_student;
CREATE TABLE parent_student
(
    parent_id  char(36),
    student_id char(36)
);

DROP TABLE IF EXISTS chat;
CREATE TABLE chat
(
    id          char(36) PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    creator_id  char(36)
);

DROP TABLE IF EXISTS chat_message;
CREATE TABLE chat_message
(
    id         char(36) PRIMARY KEY,
    chat_id    char(36),
    user_id    char(36),
    message    VARCHAR(4096),
    timestamp  TIMESTAMP,
    chat_state VARCHAR(255)
);

DROP TABLE IF EXISTS chat_user;
CREATE TABLE chat_user
(
    chat_id char(36),
    user_id char(36)
);

DROP TABLE IF EXISTS school_info;
CREATE TABLE school_info
(
    id        char(36) PRIMARY KEY,
    title     VARCHAR(255),
    message   VARCHAR(4096),
    date      DATE,
    img       char(36),
    user_id   char(36),
    important BOOLEAN,
    pinned    BOOLEAN,
    active    BOOLEAN
);

DROP TABLE IF EXISTS semester;
CREATE TABLE semester
(
    id        char(36) PRIMARY KEY,
    name      VARCHAR(250),
    date_from TIMESTAMP,
    date_to   TIMESTAMP
);

DROP TABLE IF EXISTS school_class_semester;
CREATE TABLE school_class_semester
(
    school_class_id char(36),
    semester_id     char(36)
);

DROP TABLE IF EXISTS school_config;
CREATE TABLE school_config
(
    name      VARCHAR(250),
    value     VARCHAR(250),
    code_type VARCHAR(255)
);

DROP TABLE IF EXISTS image;
CREATE TABLE image
(
    id    char(36) PRIMARY KEY,
    image MEDIUMBLOB
);

DROP TABLE IF EXISTS ChallengeQuestionAnswers;
CREATE TABLE ChallengeQuestionAnswers(
	Id char(36) NOT NULL,
	ChallengeId char(36) NOT NULL,
	ChallengeQuestionPosedId char(36) NOT NULL,
	UserId char(36) NOT NULL,
	Answer text NOT NULL,
	IsRight bit NOT NULL,
	Points int NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS ChallengeQuestionsMathematicResolved;
CREATE TABLE ChallengeQuestionsMathematicResolved(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	Answer float NOT NULL,
	Digits int NOT NULL,
	ChallengeId char(36) NOT NULL,
	QuestionMathematicId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS ChallengeQuestiosnPosed;
CREATE TABLE ChallengeQuestiosnPosed(
	Id char(36) NOT NULL,
	ChallengeId char(36) NOT NULL,
	QuestionId char(36) NOT NULL,
	Answer text NOT NULL,
	Created datetime NOT NULL,
	Expires datetime NOT NULL,
	IsActive bit NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS Challenges;
CREATE TABLE Challenges(
	Id char(36) NOT NULL,
	Name text NOT NULL,
	CreateSetId char(36) NOT NULL,
	State int NOT NULL,
	OwnerId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS ChallengeUsers;
CREATE TABLE ChallengeUsers(
	Id char(36) NOT NULL,
	UserId char(36) NOT NULL,
	ChallengeId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionDistributeAnswers;
CREATE TABLE CreateQuestionDistributeAnswers(
	Id char(36) NOT NULL,
	LeftSide text NOT NULL,
	LeftSideId char(36) NOT NULL,
	RightSide text NOT NULL,
	RightSideId char(36) NOT NULL,
	QuestionDistributeId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionDistributes;
CREATE TABLE CreateQuestionDistributes(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionMathematics;
CREATE TABLE CreateQuestionMathematics(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	Answer text NOT NULL,
	Digits int NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionMathematicVariables;
CREATE TABLE CreateQuestionMathematicVariables(
	Id char(36) NOT NULL,
	Display text NOT NULL,
	Min float NOT NULL,
	Max float NOT NULL,
	Digits int NOT NULL,
	IntervalVariable float NOT NULL,
	QuestionMathematicId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionMultipleChoiceAnswers;
CREATE TABLE CreateQuestionMultipleChoiceAnswers(
	Id char(36) NOT NULL,
	Answer text NOT NULL,
	IsRight bit NOT NULL,
	QuestionMultipleChoiceId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionMultipleChoices;
CREATE TABLE CreateQuestionMultipleChoices(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionOpenQuestions;
CREATE TABLE CreateQuestionOpenQuestions(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	Answer text NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionTextFields;
CREATE TABLE CreateQuestionTextFields(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionTrueFalses;
CREATE TABLE CreateQuestionTrueFalses(
	Id char(36) NOT NULL,
	Question text NOT NULL,
	Answer bit NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateQuestionWords;
CREATE TABLE CreateQuestionWords(
	Id char(36) NOT NULL,
	LanguageSubjectMain text NOT NULL,
	LanguageSubjectSecond text NOT NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS CreateSets;
CREATE TABLE CreateSets(
	Id char(36) NOT NULL,
	Name text NOT NULL,
	Description text NOT NULL,
	SubjectMain int NOT NULL,
	SubjectSecond int NULL,
	Created datetime NOT NULL,
	CreatedById char(36) NOT NULL,
	Modified datetime NOT NULL,
	ModifiedById char(36) NOT NULL,
	SetPolicy int NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS DrawCanvasStoragePoints;
CREATE TABLE DrawCanvasStoragePoints(
	Id char(36) NOT NULL,
	X float NOT NULL,
	Y float NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS DrawCanvasStorages;
CREATE TABLE DrawCanvasStorages(
	Id char(36) NOT NULL,
	DrawPageId char(36) NOT NULL,
	Created datetime NOT NULL,
	Deleted datetime NULL,
	Color text NOT NULL,
	FromPositionId char(36) NOT NULL,
	ToPositionId char(36) NOT NULL,
	Text text NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS DrawCollections;
CREATE TABLE DrawCollections(
	Id char(36) NOT NULL,
	Name text NOT NULL,
	OwnerId char(36) NOT NULL,
	Changed datetime NOT NULL,
	ChangedById char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS DrawGroupCollections;
CREATE TABLE DrawGroupCollections(
	Id char(36) NOT NULL,
	DrawCollectionId char(36) NOT NULL,
	GroupId char(36) NOT NULL,
	DrawGroupPolicy int NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS DrawPages;
CREATE TABLE DrawPages(
	Id char(36) NOT NULL,
	DrawCollectionId char(36) NOT NULL,
	OwnerId char(36) NOT NULL,
	Created datetime NOT NULL,
	Changed datetime NOT NULL,
	ChangedById char(36) NOT NULL,
	DataUrl text NOT NULL,
	StepperPosition datetime NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS Files;
CREATE TABLE Files(
	Id char(36) NOT NULL,
	ActualVersionId char(36) NOT NULL,
	ActualVersionFileNameExternal text NOT NULL,
	ActualVersionPath text NOT NULL,
	OwnerId char(36) NOT NULL,
	FilePolicy int NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS FilesAnonymous;
CREATE TABLE FilesAnonymous(
	Id char(36) NOT NULL,
	FileNameInternal text NOT NULL,
	FileNameExternal text NOT NULL,
	Path text NOT NULL,
	Created datetime NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS FileVersions;
CREATE TABLE FileVersions(
	Id char(36) NOT NULL,
	FileId char(36) NOT NULL,
	FileNameInternal text NOT NULL,
	FileNameExternal text NOT NULL,
	Path text NOT NULL,
	Created datetime NOT NULL,
	CreatedById char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS GroupFiles;
CREATE TABLE GroupFiles(
	Id char(36) NOT NULL,
	GroupId char(36) NOT NULL,
	FileId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS GroupMembers;
CREATE TABLE GroupMembers(
	Id char(36) NOT NULL,
	GroupId char(36) NOT NULL,
	UserId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS GroupMessages;
CREATE TABLE GroupMessages(
	Id char(36) NOT NULL,
	SenderId char(36) NOT NULL,
	GroupId char(36) NOT NULL,
	Message text NOT NULL,
	Date datetime NOT NULL,
	IsInfoMessage bit NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS `Groups`;
CREATE TABLE `Groups` (
	Id char(36) NOT NULL,
	Name text NOT NULL,
	Description text NOT NULL,
	ProfileImageId char(36) NOT NULL,
	AdminId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS LearnQuestions;
CREATE TABLE LearnQuestions(
	Id char(36) NOT NULL,
	LearnSessionId char(36) NOT NULL,
	QuestionId char(36) NOT NULL,
	Question text NOT NULL,
	Description text NULL,
	QuestionType int NOT NULL,
	PossibleAnswers text NULL,
	RightAnswer text NOT NULL,
	AnswerByUser text NULL,
	AnsweredCorrect bit NULL,
	MarkedAsHard bit NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS LearnSessions;
CREATE TABLE LearnSessions(
	Id char(36) NOT NULL,
	UserId char(36) NOT NULL,
	Created datetime NOT NULL,
	Ended datetime NULL,
	SetId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TestGroups;
CREATE TABLE TestGroups(
	Id char(36) NOT NULL,
	TestId char(36) NOT NULL,
	GroupId char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TestOfUsers;
CREATE TABLE TestOfUsers(
	Id char(36) NOT NULL,
	UserId char(36) NOT NULL,
	TestId char(36) NOT NULL,
	Started datetime NOT NULL,
	Ended datetime NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TestQuestionOfUsers;
CREATE TABLE TestQuestionOfUsers(
	Id char(36) NOT NULL,
	TestOfUserId char(36) NOT NULL,
	TestQuestionId char(36) NOT NULL,
	AnswerByUser text NULL,
	AnsweredCorrect bit NULL,
	PointsScored int NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TestQuestions;
CREATE TABLE TestQuestions(
	Id char(36) NOT NULL,
	TestId char(36) NOT NULL,
	QuestionId char(36) NOT NULL,
	Question text NOT NULL,
	Description text NULL,
	QuestionType int NOT NULL,
	PossibleAnswers text NULL,
	RightAnswer text NOT NULL,
	PointsPossible int NOT NULL,
	Visible bit NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS Tests;
CREATE TABLE Tests(
	Id char(36) NOT NULL,
	Name text NOT NULL,
	SetId char(36) NOT NULL,
	OwnerId char(36) NOT NULL,
	Created datetime NOT NULL,
	MaxTime int NOT NULL,
	Visible bit NOT NULL,
	Active bit NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TogetherAsks;
CREATE TABLE TogetherAsks(
	Id char(36) NOT NULL,
	InterestedUserId char(36) NOT NULL,
	AskedUserId char(36) NOT NULL,
	Answer bit NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TogetherConnections;
CREATE TABLE TogetherConnections(
	Id char(36) NOT NULL,
	UserId1 char(36) NOT NULL,
	UserId2 char(36) NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TogetherMessages;
CREATE TABLE TogetherMessages(
	Id char(36) NOT NULL,
	SenderId char(36) NOT NULL,
	ReceiverId char(36) NOT NULL,
	Message text NOT NULL,
	Date datetime NOT NULL,
	PRIMARY KEY(Id)
);

DROP TABLE IF EXISTS TogetherSwipes;
CREATE TABLE TogetherSwipes(
	Id char(36) NOT NULL,
	SwiperUserId char(36) NOT NULL,
	AskedUserId char(36) NOT NULL,
	Choice bit NOT NULL,
	PRIMARY KEY(Id)
);

ALTER TABLE ChallengeQuestionAnswers ADD CONSTRAINT FK_ChallengeQuestionAnswers_ChallengeQuestiosnPosed_PosedId FOREIGN KEY(ChallengeQuestionPosedId)
REFERENCES ChallengeQuestiosnPosed (Id);

ALTER TABLE ChallengeQuestionAnswers ADD CONSTRAINT FK_ChallengeQuestionAnswers_Challenges_ChallengeId FOREIGN KEY(ChallengeId)
REFERENCES Challenges (Id);

ALTER TABLE ChallengeQuestionAnswers ADD CONSTRAINT FK_ChallengeQuestionAnswers_Users_UserId FOREIGN KEY(UserId)
REFERENCES user (id);

ALTER TABLE ChallengeQuestionsMathematicResolved ADD CONSTRAINT FK_ChallengeQuestionsMathematicResolved_Challenges_ChallengeId FOREIGN KEY(ChallengeId)
REFERENCES Challenges (Id);

ALTER TABLE ChallengeQuestiosnPosed ADD CONSTRAINT FK_ChallengeQuestiosnPosed_Challenges_ChallengeId FOREIGN KEY(ChallengeId)
REFERENCES Challenges (Id);

ALTER TABLE Challenges ADD CONSTRAINT FK_Challenges_CreateSets_CreateSetId FOREIGN KEY(CreateSetId)
REFERENCES CreateSets (Id);

ALTER TABLE Challenges ADD CONSTRAINT FK_Challenges_Users_OwnerId FOREIGN KEY(OwnerId)
REFERENCES user (id);

ALTER TABLE ChallengeUsers ADD CONSTRAINT FK_ChallengeUsers_Challenges_ChallengeId FOREIGN KEY(ChallengeId)
REFERENCES Challenges (Id);

ALTER TABLE ChallengeUsers ADD CONSTRAINT FK_ChallengeUsers_Users_UserId FOREIGN KEY(UserId)
REFERENCES user (id);

ALTER TABLE CreateQuestionDistributeAnswers ADD CONSTRAINT FK_CreateQuestionDistributeAnswers_Distributes_DistributeId FOREIGN KEY(QuestionDistributeId)
REFERENCES CreateQuestionDistributes (Id);

ALTER TABLE CreateQuestionDistributes ADD CONSTRAINT FK_CreateQuestionDistributes_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionMathematics ADD CONSTRAINT FK_CreateQuestionMathematics_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionMathematicVariables ADD CONSTRAINT FK_CreateQuestionMathematicVariables_Mathematics_MathematicId FOREIGN KEY(QuestionMathematicId)
REFERENCES CreateQuestionMathematics (Id);

ALTER TABLE CreateQuestionMultipleChoiceAnswers ADD CONSTRAINT FK_CreateQuestionMultipleChoiceAnswers_MC_MultipleChoiceId FOREIGN KEY(QuestionMultipleChoiceId)
REFERENCES CreateQuestionMultipleChoices (Id);

ALTER TABLE CreateQuestionMultipleChoices ADD CONSTRAINT FK_CreateQuestionMultipleChoices_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionOpenQuestions ADD CONSTRAINT FK_CreateQuestionOpenQuestions_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionTextFields ADD CONSTRAINT FK_CreateQuestionTextFields_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionTrueFalses ADD CONSTRAINT FK_CreateQuestionTrueFalses_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateQuestionWords ADD CONSTRAINT FK_CreateQuestionWords_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE CreateSets ADD CONSTRAINT FK_CreateSets_Users_CreatedById FOREIGN KEY(CreatedById)
REFERENCES user (id);

ALTER TABLE CreateSets ADD CONSTRAINT FK_CreateSets_Users_ModifiedById FOREIGN KEY(ModifiedById)
REFERENCES user (id);

ALTER TABLE DrawCanvasStorages ADD CONSTRAINT FK_DrawCanvasStorages_DrawCanvasStoragePoints_FromPositionId FOREIGN KEY(FromPositionId)
REFERENCES DrawCanvasStoragePoints (Id);

ALTER TABLE DrawCanvasStorages ADD CONSTRAINT FK_DrawCanvasStorages_DrawCanvasStoragePoints_ToPositionId FOREIGN KEY(ToPositionId)
REFERENCES DrawCanvasStoragePoints (Id);

ALTER TABLE DrawCanvasStorages ADD CONSTRAINT FK_DrawCanvasStorages_DrawPages_DrawPageId FOREIGN KEY(DrawPageId)
REFERENCES DrawPages (Id);

ALTER TABLE DrawCollections ADD CONSTRAINT FK_DrawCollections_Users_ChangedById FOREIGN KEY(ChangedById)
REFERENCES user (id);

ALTER TABLE DrawCollections ADD CONSTRAINT FK_DrawCollections_Users_OwnerId FOREIGN KEY(OwnerId)
REFERENCES user (id);

ALTER TABLE DrawGroupCollections ADD CONSTRAINT FK_DrawGroupCollections_DrawCollections_DrawCollectionId FOREIGN KEY(DrawCollectionId)
REFERENCES DrawCollections (Id);

ALTER TABLE DrawGroupCollections ADD CONSTRAINT FK_DrawGroupCollections_Groups_GroupId FOREIGN KEY(GroupId)
REFERENCES `Groups` (Id);

ALTER TABLE DrawPages ADD CONSTRAINT FK_DrawPages_DrawCollections_DrawCollectionId FOREIGN KEY(DrawCollectionId)
REFERENCES DrawCollections (Id);

ALTER TABLE DrawPages ADD CONSTRAINT FK_DrawPages_Users_ChangedById FOREIGN KEY(ChangedById)
REFERENCES user (id);

ALTER TABLE DrawPages ADD CONSTRAINT FK_DrawPages_Users_OwnerId FOREIGN KEY(OwnerId)
REFERENCES user (id);

ALTER TABLE Files ADD CONSTRAINT FK_Files_Users_OwnerId FOREIGN KEY(OwnerId)
REFERENCES user (id);

ALTER TABLE FileVersions ADD CONSTRAINT FK_FileVersions_Files_FileId FOREIGN KEY(FileId)
REFERENCES Files (Id);

ALTER TABLE FileVersions ADD CONSTRAINT FK_FileVersions_Users_CreatedById FOREIGN KEY(CreatedById)
REFERENCES user (id)
ON DELETE CASCADE;

ALTER TABLE GroupFiles ADD CONSTRAINT FK_GroupFiles_Files_FileId FOREIGN KEY(FileId)
REFERENCES Files (Id)
ON DELETE CASCADE;

ALTER TABLE GroupFiles ADD CONSTRAINT FK_GroupFiles_Groups_GroupId FOREIGN KEY(GroupId)
REFERENCES `Groups` (Id);

ALTER TABLE GroupMembers ADD CONSTRAINT FK_GroupMembers_Groups_GroupId FOREIGN KEY(GroupId)
REFERENCES `Groups` (Id);

ALTER TABLE GroupMembers ADD CONSTRAINT FK_GroupMembers_Users_UserId FOREIGN KEY(UserId)
REFERENCES user (id);

ALTER TABLE GroupMessages ADD CONSTRAINT FK_GroupMessages_Groups_GroupId FOREIGN KEY(GroupId)
REFERENCES `Groups` (Id);

ALTER TABLE GroupMessages ADD CONSTRAINT FK_GroupMessages_Users_SenderId FOREIGN KEY(SenderId)
REFERENCES user (id);

ALTER TABLE `Groups` ADD CONSTRAINT FK_Groups_Files_ProfileImageId FOREIGN KEY(ProfileImageId)
REFERENCES Files (Id);

ALTER TABLE `Groups` ADD CONSTRAINT FK_Groups_Users_AdminId FOREIGN KEY(AdminId)
REFERENCES user (id);

ALTER TABLE LearnQuestions ADD CONSTRAINT FK_LearnQuestions_LearnSessions_LearnSessionId FOREIGN KEY(LearnSessionId)
REFERENCES LearnSessions (Id);

ALTER TABLE LearnSessions ADD CONSTRAINT FK_LearnSessions_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE LearnSessions ADD CONSTRAINT FK_LearnSessions_Users_UserId FOREIGN KEY(UserId)
REFERENCES user (id);

ALTER TABLE TestGroups ADD CONSTRAINT FK_TestGroups_Groups_GroupId FOREIGN KEY(GroupId)
REFERENCES `Groups` (Id);

ALTER TABLE TestGroups ADD CONSTRAINT FK_TestGroups_Tests_TestId FOREIGN KEY(TestId)
REFERENCES Tests (Id);

ALTER TABLE TestOfUsers ADD CONSTRAINT FK_TestOfUsers_Tests_TestId FOREIGN KEY(TestId)
REFERENCES Tests (Id);

ALTER TABLE TestOfUsers ADD CONSTRAINT FK_TestOfUsers_Users_UserId FOREIGN KEY(UserId)
REFERENCES user (id);

ALTER TABLE TestQuestionOfUsers ADD CONSTRAINT FK_TestQuestionOfUsers_TestOfUsers_TestOfUserId FOREIGN KEY(TestOfUserId)
REFERENCES TestOfUsers (id);

ALTER TABLE TestQuestionOfUsers ADD CONSTRAINT FK_TestQuestionOfUsers_TestQuestions_TestQuestionId FOREIGN KEY(TestQuestionId)
REFERENCES TestQuestions (Id);

ALTER TABLE TestQuestions ADD CONSTRAINT FK_TestQuestions_Tests_TestId FOREIGN KEY(TestId)
REFERENCES Tests (Id);

ALTER TABLE Tests ADD CONSTRAINT FK_Tests_CreateSets_SetId FOREIGN KEY(SetId)
REFERENCES CreateSets (Id);

ALTER TABLE Tests ADD CONSTRAINT FK_Tests_Users_OwnerId FOREIGN KEY(OwnerId)
REFERENCES user (id);

ALTER TABLE TogetherAsks ADD CONSTRAINT FK_TogetherAsks_Users_AskedUserId FOREIGN KEY(AskedUserId)
REFERENCES user (id);

ALTER TABLE TogetherAsks ADD CONSTRAINT FK_TogetherAsks_Users_InterestedUserId FOREIGN KEY(InterestedUserId)
REFERENCES user (id);

ALTER TABLE TogetherConnections ADD CONSTRAINT FK_TogetherConnections_Users_UserId1 FOREIGN KEY(UserId1)
REFERENCES user (id);

ALTER TABLE TogetherConnections ADD CONSTRAINT FK_TogetherConnections_Users_UserId2 FOREIGN KEY(UserId2)
REFERENCES user (id);

ALTER TABLE TogetherMessages ADD CONSTRAINT FK_TogetherMessages_Users_ReceiverId FOREIGN KEY(ReceiverId)
REFERENCES user (id);

ALTER TABLE TogetherMessages ADD CONSTRAINT FK_TogetherMessages_Users_SenderId FOREIGN KEY(SenderId)
REFERENCES user (id);

ALTER TABLE TogetherSwipes ADD CONSTRAINT FK_TogetherSwipes_Users_AskedUserId FOREIGN KEY(AskedUserId)
REFERENCES user (id);

ALTER TABLE TogetherSwipes ADD CONSTRAINT FK_TogetherSwipes_Users_SwiperUserId FOREIGN KEY(SwiperUserId)
REFERENCES user (id);

ALTER TABLE token ADD CONSTRAINT FK_Token_User_UserId FOREIGN KEY(user_id)
REFERENCES user (id);