-- Insert Courses
INSERT INTO courses (id, code, name, description, credits, created_at, updated_at) VALUES
(1, 'CS101', 'Introduction to Programming', 'Fundamentals of programming using Python', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'CS102', 'Data Structures', 'Study of fundamental data structures and algorithms', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'CS201', 'Object-Oriented Programming', 'Advanced programming concepts and OOP principles', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'CS202', 'Database Systems', 'Introduction to relational databases and SQL', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'CS301', 'Web Development', 'Modern web development with frameworks', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'CS302', 'Software Engineering', 'Software development lifecycle and methodologies', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'CS303', 'Algorithms', 'Advanced algorithm design and analysis', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'CS401', 'Cloud Computing', 'Cloud architecture and distributed systems', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'CS402', 'Machine Learning', 'Introduction to ML algorithms and applications', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'MATH101', 'Discrete Mathematics', 'Logic, sets, and mathematical reasoning', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'MATH201', 'Linear Algebra', 'Vectors, matrices, and linear transformations', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'CS304', 'Operating Systems', 'Concepts of modern operating systems', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'CS305', 'Computer Networks', 'Network protocols and architecture', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'CS403', 'Artificial Intelligence', 'AI techniques and intelligent agents', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Prerequisites
-- CS102 requires CS101
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(2, 1, CURRENT_TIMESTAMP);

-- CS201 requires CS101
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(3, 1, CURRENT_TIMESTAMP);

-- CS202 requires CS101
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(4, 1, CURRENT_TIMESTAMP);

-- CS301 requires CS201 and CS202
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(5, 3, CURRENT_TIMESTAMP),
(5, 4, CURRENT_TIMESTAMP);

-- CS302 requires CS201 and CS102
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(6, 3, CURRENT_TIMESTAMP),
(6, 2, CURRENT_TIMESTAMP);

-- CS303 requires CS102 and MATH101
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(7, 2, CURRENT_TIMESTAMP),
(7, 10, CURRENT_TIMESTAMP);

-- CS401 requires CS301 and CS304
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(8, 5, CURRENT_TIMESTAMP),
(8, 12, CURRENT_TIMESTAMP);

-- CS402 requires CS303 and MATH201
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(9, 7, CURRENT_TIMESTAMP),
(9, 11, CURRENT_TIMESTAMP);

-- CS304 requires CS102 and CS201
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(12, 2, CURRENT_TIMESTAMP),
(12, 3, CURRENT_TIMESTAMP);

-- CS305 requires CS102
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(13, 2, CURRENT_TIMESTAMP);

-- CS403 requires CS402 and CS303
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(14, 9, CURRENT_TIMESTAMP),
(14, 7, CURRENT_TIMESTAMP);

-- MATH201 requires MATH101
INSERT INTO prerequisites (course_id, prerequisite_course_id, created_at) VALUES
(11, 10, CURRENT_TIMESTAMP);

