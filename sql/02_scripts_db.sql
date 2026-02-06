-- ================================================
-- INSERTAR AUTORES (12 autores)
-- ================================================
INSERT INTO authors
    (full_name)
VALUES
    ('SenLin Yu'),
    ('Leigh Bardugo'),
    ('Kerri Maniscalco'),
    ('Patrick Rothfuss'),
    ('Rainbow Rowell'),
    ('Katherine Neville'),
    ('Antoine de Saint-Exupery'),
    ('Franz Kafka'),
    ('Paulo Coelho'),
    ('Charlotte Bronte'),
    ('J.K. Rowling'),
    ('Michelle Zauner');


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
    ('Alchemised', '978-0-000-00001-0', 'Una emocionante historia de romance oscuro y espionaje.');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Seis de Cuervos', '978-0-451-65790-5', 'Seis peligrosos marginados intentan un robo imposible');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Trono de los Caidos, El', '978-0-000-00003-0', 'Un romántico y gótico cuento con secretos oscuros y amor prohibido');

-- Ingrid's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Puertas de Piedra, Las ', '978-0-000-00004-0', 'Una emocionante aventura a través de un mundo de magia y misterio');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Carry On', '978-1-250-05552-0', 'Una reimaginación mágica de los estereotipos clásicos del fantasy');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Ocho, El ', '978-0-000-00006-0', 'Una emocionante travesía que mezcla el pasado y el presente');

-- Geraldine's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Principito, El ', '978-0-465-06959-7', 'Una historia atemporal sobre amor, pérdida y el significado de la vida');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Metamorfosis, La ', '978-0-000-00008-0', 'Un hombre despierta una mañana transformado en un insecto');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Alquimista, El ', '978-0-062-31609-7', 'Un pastor boy busca tesoro y encuentra su destino');

-- Leonela's books
INSERT INTO books
    (title, isbn, description)
VALUES
    ('Jane Eyre', '978-0-141-24081-5', 'Una joven independiente encuentra el amor en una mansión gótica.');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Animales Fantasticos y Donde Encontrarlos', '978-0-439-70818-8', 'Una guía sobre criaturas mágicas en el mundo mágico');

INSERT INTO books
    (title, isbn, description)
VALUES
    ('Crying in H Mart', '978-0-525-55918-4', 'Un recuerdo sobre identidad, dolor y comida coreana');


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
        WHERE full_name = 'SenLin Yu')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-451-65790-5'), (SELECT id
        FROM authors
        WHERE full_name = 'Leigh Bardugo')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00003-0'), (SELECT id
        FROM authors
        WHERE full_name = 'Kerri Maniscalco')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00004-0'), (SELECT id
        FROM authors
        WHERE full_name = 'Patrick Rothfuss')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-1-250-05552-0'), (SELECT id
        FROM authors
        WHERE full_name = 'Rainbow Rowell')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00006-0'), (SELECT id
        FROM authors
        WHERE full_name = 'Katherine Neville')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-465-06959-7'), (SELECT id
        FROM authors
        WHERE full_name = 'Antoine de Saint-Exupery')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-000-00008-0'), (SELECT id
        FROM authors
        WHERE full_name = 'Franz Kafka')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-062-31609-7'), (SELECT id
        FROM authors
        WHERE full_name = 'Paulo Coelho')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-141-24081-5'), (SELECT id
        FROM authors
        WHERE full_name = 'Charlotte Bronte')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-439-70818-8'), (SELECT id
        FROM authors
        WHERE full_name = 'J.K. Rowling')),
    ((SELECT id
        FROM books
        WHERE isbn = '978-0-525-55918-4'), (SELECT id
        FROM authors
        WHERE full_name = 'Michelle Zauner'));


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
