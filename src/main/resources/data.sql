-- Adding initial data from CSV files

-- Adding genres
INSERT INTO genre (genre_id, name) VALUES
    ('adventure', 'Adventure'),
    ('animation', 'Animation'),
    ('children', 'Children'),
    ('comedy', 'Comedy'),
    ('fantasy', 'Fantasy'),
    ('romance', 'Romance'),
    ('action', 'Action'),
    ('thriller', 'Thriller'),
    ('scifi', 'Sci-Fi'),
    ('drama', 'Drama'),
    ('musical', 'Musical'),
    ('crime', 'Crime'),
    ('biography', 'Biography');

-- Adding movies
INSERT INTO movie (title) VALUES
    ('Toy Story'),
    ('Grumpier Old Men'),
    ('Die Hard'),
    ('Star Wars: Return of the Jedi'),
    ('The Lion King'),
    ('Pulp Fiction'),
    ('Forrest Gump'),
    ('The Matrix'),
    ('Goodfellas'),
    ('Jurassic Park');

-- Adding genres to movies
INSERT INTO movie_genre (movie_id, genre_id) VALUES
    (1, 'adventure'), (1, 'animation'), (1, 'children'), (1, 'comedy'), (1, 'fantasy'),
    (2, 'comedy'), (2, 'romance'),
    (3, 'action'), (3, 'thriller'),
    (4, 'action'), (4, 'adventure'), (4, 'fantasy'), (4, 'scifi'),
    (5, 'adventure'), (5, 'animation'), (5, 'children'), (5, 'drama'), (5, 'musical'),
    (6, 'drama'), (6, 'crime'), (6, 'thriller'),
    (7, 'comedy'), (7, 'drama'), (7, 'romance'),
    (8, 'action'), (8, 'scifi'),
    (9, 'drama'), (9, 'crime'), (9, 'biography'),
    (10, 'adventure'), (10, 'thriller'), (10, 'scifi');

-- Adding users
INSERT INTO user (username) VALUES
    ('Alice'),
    ('Bob'),
    ('Charlie');

-- Adding interactions
INSERT INTO interaction (user_id, movie_id, rating, view_percentage) VALUES
    (1, 1, 4, 85),
    (1, 2, 5, NULL),
    (2, 1, NULL, 90),
    (2, 3, 2, NULL),
    (3, 4, NULL, 70),
    (3, 2, 1, NULL);