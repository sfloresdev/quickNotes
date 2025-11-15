INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (1, 'Reunión equipo', 'Reunión de seguimiento del proyecto a las 10:00 AM', 'work', '2025-11-15', true, false, 'blue');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (2, 'Lista de compras', 'Leche, pan, huevos, frutas, verduras', 'personal', '2025-11-14', false, false, 'yellow');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (3, 'Recordatorio médico', 'Cita con el dentista el lunes a las 16:00', 'reminder', '2025-11-13', false, false, 'green');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (4, 'Idea app', 'Desarrollar aplicación de notas con recordatorios y colores', 'personal', '2025-11-12', true, false, 'purple');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (5, 'Tareas pendientes', 'Finalizar documentación técnica y pruebas unitarias', 'work', '2025-11-11', false, true, 'red');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (6, 'Cumpleaños Ana', 'Comprar regalo y enviar tarjeta de felicitación', 'personal', '2025-11-10', false, false, 'orange');

INSERT INTO notes (id, title, content, type, creation_date, is_pinned, is_archived, color)
VALUES (7, 'Libro recomendado', 'Leer Clean Code para mejorar prácticas de programación', 'personal', '2025-11-09', false, false, 'pink');

SELECT setval('notes_id_seq', (SELECT MAX(id) FROM notes));