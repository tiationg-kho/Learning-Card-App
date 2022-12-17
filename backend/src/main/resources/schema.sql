CREATE TABLE IF NOT EXISTS students (
                                        studentId INT PRIMARY KEY AUTO_INCREMENT,
                                        email VARCHAR(60) NOT NULL UNIQUE,
                                        pwd VARCHAR(60) NOT NULL,
                                        createdDate TIMESTAMP NOT NULL,
                                        modifiedDate TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS cardsets (
                                        cardsetId INT PRIMARY KEY AUTO_INCREMENT,
                                        title VARCHAR(60) NOT NULL,
                                        cardsetCategory VARCHAR(60) NOT NULL,
                                        cardsetStatus VARCHAR(60) NOT NULL,
                                        createdDate TIMESTAMP NOT NULL,
                                        modifiedDate TIMESTAMP NOT NULL,
                                        studentId INT NOT NULL,
                                        FOREIGN KEY (studentId) REFERENCES students(studentId)
                                            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cards (
                                     cardId INT PRIMARY KEY AUTO_INCREMENT,
                                     upTitle VARCHAR(60) NOT NULL,
                                     upContent VARCHAR(480) NOT NULL,
                                     downTitle VARCHAR(60) NOT NULL,
                                     downContent VARCHAR(480) NOT NULL,
                                     createdDate TIMESTAMP NOT NULL,
                                     modifiedDate TIMESTAMP NOT NULL,
                                     cardsetId INT NOT NULL,
                                     FOREIGN KEY (cardsetId) REFERENCES cardsets(cardsetId)
                                         ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
                                        commentId INT PRIMARY KEY AUTO_INCREMENT,
                                        title VARCHAR(60) NOT NULL,
                                        content VARCHAR(240) NOT NULL,
                                        createdDate TIMESTAMP NOT NULL,
                                        modifiedDate TIMESTAMP NOT NULL,
                                        commenterId INT,
                                        cardId INT NOT NULL,
                                        FOREIGN KEY (cardId) REFERENCES cards(cardId)
                                            ON DELETE CASCADE,
                                        FOREIGN KEY (commenterId) REFERENCES students(studentId)
                                            ON DELETE SET NULL
);