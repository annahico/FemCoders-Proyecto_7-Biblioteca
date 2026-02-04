-- ================================================
-- INSERTAR AUTORES (12 autores)
-- ================================================
INSERT INTO authors
    (first_name, last_name)
VALUES
    ('SenLin', 'Yu'),
    ('Leigh', 'Bardugo'),
    ('Kerri', 'Maniscalco'),
    ('Patrick', 'Rothfuss'),
    ('Rainbow', 'Rowell'),
    ('Katherine', 'Neville'),
    ('Antoine', 'de Saint-Exupery'),
    ('Franz', 'Kafka'),
    ('Paulo', 'Coelho'),
    ('Charlotte', 'Bronte'),
    ('J.K.', 'Rowling'),
    ('Michelle', 'Zauner');


-- ================================================
-- INSERTAR GÉNEROS
-- ================================================
INSERT INTO genres
    (name)
VALUES
    ('Dark Romance'),
    ('Psychological Drama'),
    ('Espionage'),
    ('Epic Fantasy'),
    ('Heist Fiction'),
    ('Young Adult'),
    ('Romantasy'),
    ('Gothic Fantasy'),
    ('New Adult'),
    ('Heroic Fantasy'),
    ('High Fantasy'),
    ('Novel'),
    ('Fantasy Literature'),
    ('Urban Fantasy'),
    ('Suspense'),
    ('Science Fiction'),
    ('Speculative Fiction'),
    ('Novella'),
    ('Children''s Literature'),
    ('Drama'),
    ('Gothic Fiction'),
    ('Victorian Literature'),
    ('Romance'),
    ('Fantasy'),
    ('Adventure'),
    ('Memoir'),
    ('Autobiography'),
    ('Food Writing');


-- ================================================
-- INSERTAR LIBROS (12 libros - 3 por cada miembro)
-- ================================================

-- Anna's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Alchemised', '978-0-000-00001-0', 'A thrilling tale of dark romance and espionage');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Seis de Cuervos', '978-0-451-65790-5', 'Six dangerous outcasts attempt an impossible heist');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Trono de los Caidos, El', '978-0-000-00003-0', 'A gothic romantasy with dark secrets and forbidden love');

-- Ingrid's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Puertas de Piedra, Las ', '978-0-000-00004-0', 'An epic journey through a world of magic and mystery');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Carry On', '978-1-250-05552-0', 'A magical reimagining of classic fantasy tropes');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Ocho, El ', '978-0-000-00006-0', 'A suspenseful journey blending past and present');

-- Geraldine's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Principito, El ', '978-0-465-06959-7', 'A timeless story about love, loss and the meaning of life');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Metamorfosis, La ', '978-0-000-00008-0', 'A man wakes up one morning transformed into an insect');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Alquimista, El ', '978-0-062-31609-7', 'A shepherd boy searches for treasure and finds his destiny');

-- Leonela's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Jane Eyre', '978-0-141-24081-5', 'An independent young woman finds love in a gothic mansion');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Animales Fantasticos y Donde Encontrarlos', '978-0-439-70818-8', 'A guide to magical creatures in the wizarding world');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Crying in H Mart', '978-0-525-55918-4', 'A memoir about identity, grief and Korean food');


-- ================================================
-- INSERTAR BOOKS_AUTHORS (conectar libros con autores)
-- ================================================
INSERT INTO books_authors
    (book_id, author_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00001-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Yu')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-451-65790-5'), (SELECT id
        FROM authors
        WHERE last_name = 'Bardugo')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00003-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Maniscalco')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00004-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Rothfuss')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-1-250-05552-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Rowell')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00006-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Neville')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-465-06959-7'), (SELECT id
        FROM authors
        WHERE last_name = 'de Saint-Exupery')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00008-0'), (SELECT id
        FROM authors
        WHERE last_name = 'Kafka')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-062-31609-7'), (SELECT id
        FROM authors
        WHERE last_name = 'Coelho')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-141-24081-5'), (SELECT id
        FROM authors
        WHERE last_name = 'Bronte')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-439-70818-8'), (SELECT id
        FROM authors
        WHERE last_name = 'Rowling')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-525-55918-4'), (SELECT id
        FROM authors
        WHERE last_name = 'Zauner'));


-- ================================================
-- INSERTAR BOOKS_GENRES (conectar libros con géneros)
-- ================================================

-- Alchemised = Dark Romance, Psychological Drama, Espionage
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00001-0'), (SELECT id
        FROM genres
        WHERE name = 'Dark Romance')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00001-0'), (SELECT id
        FROM genres
        WHERE name = 'Psychological Drama')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00001-0'), (SELECT id
        FROM genres
        WHERE name = 'Espionage'));

-- Seis de Cuervos = Epic Fantasy, Heist Fiction, Young Adult
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-451-65790-5'), (SELECT id
        FROM genres
        WHERE name = 'Epic Fantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-451-65790-5'), (SELECT id
        FROM genres
        WHERE name = 'Heist Fiction')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-451-65790-5'), (SELECT id
        FROM genres
        WHERE name = 'Young Adult'));

-- El Trono de los Caidos = Romantasy, Gothic Fantasy, New Adult
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00003-0'), (SELECT id
        FROM genres
        WHERE name = 'Romantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00003-0'), (SELECT id
        FROM genres
        WHERE name = 'Gothic Fantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00003-0'), (SELECT id
        FROM genres
        WHERE name = 'New Adult'));

-- Las Puertas de Piedra = Epic Fantasy, Heroic Fantasy, High Fantasy
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00004-0'), (SELECT id
        FROM genres
        WHERE name = 'Epic Fantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00004-0'), (SELECT id
        FROM genres
        WHERE name = 'Heroic Fantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00004-0'), (SELECT id
        FROM genres
        WHERE name = 'High Fantasy'));

-- Carry On = Novel, Fantasy Literature, Urban Fantasy
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-1-250-05552-0'), (SELECT id
        FROM genres
        WHERE name = 'Novel')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-1-250-05552-0'), (SELECT id
        FROM genres
        WHERE name = 'Fantasy Literature')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-1-250-05552-0'), (SELECT id
        FROM genres
        WHERE name = 'Urban Fantasy'));

-- El Ocho = Suspense, Science Fiction, Speculative Fiction
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00006-0'), (SELECT id
        FROM genres
        WHERE name = 'Suspense')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00006-0'), (SELECT id
        FROM genres
        WHERE name = 'Science Fiction')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00006-0'), (SELECT id
        FROM genres
        WHERE name = 'Speculative Fiction'));

-- El Principito = Novella, Children's Literature, Fantasy
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-465-06959-7'), (SELECT id
        FROM genres
        WHERE name = 'Novella')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-465-06959-7'), (SELECT id
        FROM genres
        WHERE name = 'Children''s Literature')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-465-06959-7'), (SELECT id
        FROM genres
        WHERE name = 'Fantasy'));

-- La Metamorfosis = Novella, Fantasy Literature
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00008-0'), (SELECT id
        FROM genres
        WHERE name = 'Novella')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00008-0'), (SELECT id
        FROM genres
        WHERE name = 'Fantasy Literature'));

-- El Alquimista = Novel, Drama
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-062-31609-7'), (SELECT id
        FROM genres
        WHERE name = 'Novel')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-062-31609-7'), (SELECT id
        FROM genres
        WHERE name = 'Drama'));

-- Jane Eyre = Gothic Fiction, Victorian Literature, Romance
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-141-24081-5'), (SELECT id
        FROM genres
        WHERE name = 'Gothic Fiction')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-141-24081-5'), (SELECT id
        FROM genres
        WHERE name = 'Victorian Literature')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-141-24081-5'), (SELECT id
        FROM genres
        WHERE name = 'Romance'));

-- Animales Fantasticos = Fantasy, Adventure, Young Adult
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-439-70818-8'), (SELECT id
        FROM genres
        WHERE name = 'Fantasy')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-439-70818-8'), (SELECT id
        FROM genres
        WHERE name = 'Adventure')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-439-70818-8'), (SELECT id
        FROM genres
        WHERE name = 'Young Adult'));

-- Crying in H Mart = Memoir, Autobiography, Food Writing
INSERT INTO books_genres
    (book_id, genre_id)
VALUES
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-525-55918-4'), (SELECT id
        FROM genres
        WHERE name = 'Memoir')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-525-55918-4'), (SELECT id
        FROM genres
        WHERE name = 'Autobiography')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-525-55918-4'), (SELECT id
        FROM genres
        WHERE name = 'Food Writing'));
