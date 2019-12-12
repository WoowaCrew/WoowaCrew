INSERT INTO user (id, oauth_id, role, degree_id) VALUES (1, '1234','ROLE_ADMIN',1);

INSERT INTO article (id, title, content, author) VALUES (1, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (2, 'delete article', 'delete content', 1);

INSERT INTO keyword(content, views) VALUES ('test A', 1L);
INSERT INTO keyword(content, views) VALUES ('test B', 1L);
INSERT INTO keyword(content, views) VALUES ('test C', 1L);
INSERT INTO keyword(content, views) VALUES ('test D', 1L);
INSERT INTO keyword(content, views) VALUES ('test E', 1L);

INSERT INTO keyword(content, views) VALUES ('중복된 검색어', 1L);

INSERT INTO keyword(content, views) VALUES ('최다 조회수 A', 300L);
INSERT INTO keyword(content, views) VALUES ('최다 조회수 B', 200L);
INSERT INTO keyword(content, views) VALUES ('최다 조회수 C', 100L);

INSERT INTO user (id, oauth_id, role, degree_id) VALUES (2, '10', 'ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, role, degree_id) VALUES (3, '11', 'ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, role, degree_id) VALUES (4, '12', 'ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, role, degree_id) VALUES (5, '13', 'ROLE_ADMIN', 1);
INSERT INTO user (id, oauth_id, role, degree_id) VALUES (6, '14', 'ROLE_COACH', 1);
INSERT INTO user (id, oauth_id, role, degree_id) VALUES (7, '15', 'ROLE_CREW', 2);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (8, '15','woowacrew','ROLE_PRECOURSE', 1);