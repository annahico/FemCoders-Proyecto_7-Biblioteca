-- ================================================
-- Queries para verificar que la base de datos
-- está correctamente poblada
-- ================================================


-- ================================================
-- VERIFICACIÓN 1: Contar registros en cada tabla
-- ================================================
SELECT 'authors' AS tabla, COUNT(*) AS filas FROM authors
UNION ALL
SELECT 'books', COUNT(*) FROM books
UNION ALL
SELECT 'genres', COUNT(*) FROM genres
UNION ALL
SELECT 'books_authors', COUNT(*) FROM books_authors
UNION ALL
SELECT 'books_genres', COUNT(*) FROM books_genres;

-- Resultado esperado:
-- authors        = 12 filas
-- books          = 12 filas
-- genres         = 28 filas
-- books_authors  = 12 filas
-- books_genres   = 34 filas (aproximadamente, algunos libros tienen más géneros)


-- ================================================
-- VERIFICACIÓN 2: Ver todos los autores
-- ================================================
SELECT * FROM authors ORDER BY full_name;


-- ================================================
-- VERIFICACIÓN 3: Ver todos los libros
-- ================================================
SELECT * FROM books ORDER BY title;


-- ================================================
-- VERIFICACIÓN 4: Ver todos los géneros
-- ================================================
SELECT * FROM genres ORDER BY name;


-- ================================================
-- VERIFICACIÓN 5: Ver todos los libros con sus autores
-- ================================================
SELECT
    b.id,
    b.title,
    a.full_name AS author
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
ORDER BY b.title;


-- ================================================
-- VERIFICACIÓN 6: Ver todos los libros con sus géneros
-- ================================================
SELECT
    b.id,
    b.title,
    g.name AS genre
FROM books b
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
ORDER BY b.title, g.name;


-- ================================================
-- VERIFICACIÓN 7: Vista completa - Libros con autores y géneros
-- Esta es la query principal que muestra todo
-- ================================================
SELECT
b.id,
b.title AS title,
b.isbn,
a.full_name AS author,
ARRAY_TO_STRING(ARRAY_AGG(DISTINCT g.name ORDER BY g.name), ', ') AS genre
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
GROUP BY b.id, b.title, b.isbn, a.full_name
ORDER BY b.id;


-- ================================================
-- VERIFICACIÓN 8: Libros por cada miembro del equipo
-- ================================================

-- Libros de Anna (ids 1-3)
SELECT b.id, b.title, a.full_name AS author
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
WHERE b.id BETWEEN 1 AND 3
ORDER BY b.id;

-- Libros de Ingrid (ids 4-6)
SELECT b.id, b.title, a.full_name AS author
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
WHERE b.id BETWEEN 4 AND 6
ORDER BY b.id;

-- Libros de Geraldine (ids 7-9)
SELECT b.id, b.title, a.full_name AS author
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
WHERE b.id BETWEEN 7 AND 9
ORDER BY b.id;

-- Libros de Leonela (ids 10-12)
SELECT b.id, b.title, a.full_name AS author
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
WHERE b.id BETWEEN 10 AND 12
ORDER BY b.id;

-- ================================================
-- VERIFICACIÓN 9: Verificar que NO hay libros sin autor
-- ================================================
SELECT 
    COUNT(*) AS libros_sin_autor,
    CASE 
        WHEN COUNT(*) = 0 THEN 'OK: All books have an author'
        ELSE 'ERROR: There are books without an assigned author'
    END AS status
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
WHERE ba.author_id IS NULL;

-- ================================================
-- VERIFICACIÓN EXTRA: Verificar integridad de datos
-- ================================================

-- 1. Verificar libros sin géneros
SELECT 
    COUNT(*) AS books_without_genre,
    CASE 
        WHEN COUNT(*) = 0 THEN 'OK: All books have a genre'
        ELSE 'WARNING: Some books have no assigned genre'
    END AS status
FROM books b
LEFT JOIN books_genres bg ON b.id = bg.book_id
WHERE bg.genre_id IS NULL;

-- 2. Verificar autores sin libros
SELECT 
    COUNT(*) AS authors_without_books,
    CASE 
        WHEN COUNT(*) = 0 THEN 'OK: All authors have books'
        ELSE 'WARNING: Some authors have no assigned books'
    END AS status
FROM authors a
LEFT JOIN books_authors ba ON a.id = ba.author_id
WHERE ba.book_id IS NULL;

-- 3. Contar total de libros
SELECT COUNT(*) AS total_books FROM books;

-- 4. Mostrar distribución de libros por autor
SELECT 
    a.full_name AS author,
    COUNT(b.id) AS book_count
FROM authors a
LEFT JOIN books_authors ba ON a.id = ba.author_id
LEFT JOIN books b ON ba.book_id = b.id
GROUP BY a.id, a.full_name
ORDER BY book_count DESC, a.full_name;


-- ================================================
-- VERIFICACIÓN 10: Verificar que NO hay libros sin género
-- ================================================

SELECT b.id, b.title
FROM books b
LEFT JOIN books_genres bg ON b.id = bg.book_id
WHERE bg.genre_id IS NULL
ORDER BY b.id;

SELECT 
    COUNT(*) AS books_without_genre,
    CASE 
        WHEN COUNT(*) = 0 THEN 'SUCCESS: All books have genre'
        ELSE 'FAILED: There are books without genre'
    END AS status
FROM books b
LEFT JOIN books_genres bg ON b.id = bg.book_id
WHERE bg.genre_id IS NULL;

-- Resultado esperado: 0 filas (todos los libros deben tener al menos un género)


-- ================================================
-- VERIFICACIÓN 11: Estadísticas generales
-- ================================================
SELECT 
    'Total number of books' AS statistic,
    COUNT(*) AS value
FROM books
UNION ALL
SELECT 
    'Total number of authors',
    COUNT(*)
FROM authors
UNION ALL
SELECT 
    'Total number of genres',
    COUNT(*)
FROM genres
UNION ALL
SELECT 
    'Average number of genres per book',
    ROUND(AVG(genre_count), 2)
FROM (
    SELECT book_id, COUNT(*) AS genre_count
    FROM books_genres
    GROUP BY book_id
) AS subquery;


-- ================================================
-- VERIFICACIÓN 12: Integridad de ISBN
-- Verificar que no hay ISBNs duplicados
-- ================================================
SELECT '=== VERIFICATION 12 ===' AS title;

SELECT
    isbn,
    COUNT(*) AS count,
    CASE
        WHEN COUNT(*) > 1 THEN 'DUPLICATE'
        ELSE 'UNIQUE'
    END AS status
FROM books
GROUP BY isbn
HAVING COUNT(*) > 1
ORDER BY count DESC, isbn;

SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 'THERE ARE NO DUPLICATE ISBNs'
        ELSE CONCAT('THERE ARE ', COUNT(*)::text, ' DUPLICATE ISBNs')
    END AS final_result
FROM (
    SELECT isbn
    FROM books
    GROUP BY isbn
    HAVING COUNT(*) > 1
) AS duplicates;

-- Resultado esperado: 0 filas (cada ISBN debe ser único)
