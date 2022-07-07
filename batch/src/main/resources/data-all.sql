DROP TABLE IF EXISTS amazon ;

CREATE TABLE amazon  (
    asin VARCHAR(20) NOT NULL PRIMARY KEY,
    main_cat TEXT,
    title TEXT,
    category TEXT,
    imageURLHighRes TEXT
);