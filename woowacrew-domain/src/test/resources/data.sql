INSERT INTO DEGREE (id, degree_number) VALUES (1, 0);
INSERT INTO DEGREE (id, degree_number) VALUES (2, 1);

INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (1, '1234','woowacrew','ROLE_ADMIN',1);

INSERT INTO article (id, title, content, author) VALUES (1, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (2, 'delete article', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (3, 'test article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (4, 'article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (5, 'specarticle', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (6, 'article', 'test spec content', 1);
INSERT INTO article (id, title, content, author) VALUES (7, 'article', 'testspec content', 1);
INSERT INTO article (id, title, content, author) VALUES (8, 'article', 'spec', 1);
INSERT INTO article (id, title, content, author) VALUES (9, 'spec', 'test spec', 1);
INSERT INTO article (id, title, content, author) VALUES (10, 'spec2', 'test spec2', 1);
INSERT INTO article (id, title, content, author) VALUES (11, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (12, 'delete article', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (13, 'test article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (14, 'article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (15, 'specarticle', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (16, 'article', 'test spec content', 1);
INSERT INTO article (id, title, content, author) VALUES (17, 'article', 'testspec content', 1);
INSERT INTO article (id, title, content, author) VALUES (18, 'article', 'spec', 1);
INSERT INTO article (id, title, content, author) VALUES (19, 'spec', 'test spec', 1);
INSERT INTO article (id, title, content, author) VALUES (20, 'spec2', 'test spec2', 1);
INSERT INTO article (id, title, content, author) VALUES (21, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (22, 'delete article', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (23, 'test article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (24, 'article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (25, 'specarticle', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (26, 'article', 'test spec content', 1);
INSERT INTO article (id, title, content, author) VALUES (27, 'article', 'testspec content', 1);
INSERT INTO article (id, title, content, author) VALUES (28, 'article', 'spec', 1);
INSERT INTO article (id, title, content, author) VALUES (29, 'spec', 'test spec', 1);
INSERT INTO article (id, title, content, author) VALUES (30, 'spec2', 'test spec2', 1);
INSERT INTO article (id, title, content, author) VALUES (31, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (32, 'delete article', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (33, 'test article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (34, 'article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (35, 'specarticle', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (36, 'article', 'test spec content', 1);
INSERT INTO article (id, title, content, author) VALUES (37, 'article', 'testspec content', 1);
INSERT INTO article (id, title, content, author) VALUES (38, 'article', 'spec', 1);
INSERT INTO article (id, title, content, author) VALUES (39, 'spec', 'test spec', 1);
INSERT INTO article (id, title, content, author) VALUES (40, 'spec2', 'test spec2', 1);
INSERT INTO article (id, title, content, author) VALUES (41, 'article A', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (42, 'delete article', 'content', 1);
INSERT INTO article (id, title, content, author) VALUES (43, 'test article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (44, 'article spec', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (45, 'specarticle', 'testdelete content', 1);
INSERT INTO article (id, title, content, author) VALUES (46, 'article', 'test spec content', 1);
INSERT INTO article (id, title, content, author) VALUES (47, 'article', 'testspec content', 1);
INSERT INTO article (id, title, content, author) VALUES (48, 'article', 'spec', 1);
INSERT INTO article (id, title, content, author) VALUES (49, 'spec', 'test spec', 1);
INSERT INTO article (id, title, content, author) VALUES (50, 'spec2', 'test spec2', 1);

INSERT INTO comment (id, content, author, article) VALUES (1, 'comment content1', 1, 1);
INSERT INTO comment (id, content, author, article) VALUES (2, 'comment content2', 1, 1);

INSERT INTO keyword(content, views) VALUES ('test A', 1L);
INSERT INTO keyword(content, views) VALUES ('test B', 1L);
INSERT INTO keyword(content, views) VALUES ('test C', 1L);
INSERT INTO keyword(content, views) VALUES ('test D', 1L);
INSERT INTO keyword(content, views) VALUES ('test E', 1L);

INSERT INTO keyword(content, views) VALUES ('중복된 검색어', 1L);

INSERT INTO keyword(content, views) VALUES ('최다 조회수 A', 300L);
INSERT INTO keyword(content, views) VALUES ('최다 조회수 B', 200L);
INSERT INTO keyword(content, views) VALUES ('최다 조회수 C', 100L);

INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (2, '10','woowacrew','ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (3, '11','woowacrew','ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (4, '12','woowacrew','ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (5, '13','woowacrew','ROLE_ADMIN', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (6, '14','woowacrew','ROLE_COACH', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (7, '15','woowacrew','ROLE_CREW', 2);
INSERT INTO user (id, oauth_id, nickname, role, degree_id) VALUES (8, '15','woowacrew','ROLE_PRECOURSE', 1);
INSERT INTO user (id, oauth_id, nickname, role, degree_id, birthday) VALUES (9, '10','woowacrew1','ROLE_PRECOURSE', 1, '1991-06-15');
INSERT INTO user (id, oauth_id, nickname, role, degree_id, birthday) VALUES (10, '11','woowacrew2','ROLE_PRECOURSE', 1, '1995-05-20');
INSERT INTO user (id, oauth_id, nickname, role, degree_id, birthday) VALUES (11, '12','woowacrew3','ROLE_PRECOURSE', 1, '1995-06-12');
INSERT INTO user (id, oauth_id, nickname, role, degree_id, birthday) VALUES (12, '12','woowacrew3','ROLE_PRECOURSE', 1, '1995-06-08');
INSERT INTO user (id, oauth_id, nickname, role, degree_id, github_id) VALUES (13, '13','woowacrew4','ROLE_PRECOURSE', 1, 'githubId');

INSERT INTO feed_source(id,source_url,description) values (1,'https://vsh123.github.io/feed.xml', 'SHAKEVAN');
INSERT INTO feed_source(id,source_url,description) values (2,'https://jojoldu.tistory.com/feed', 'TEST');

INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(1,'title1','link1','2019-10-01T00:00:00',1);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(2,'title2','link2','2019-10-02T00:00:00',1);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(3,'title3','link3','2019-10-03T00:00:00',1);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(4,'title4','link4','2019-10-04T00:00:00',1);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(5,'title5','link5','2019-10-05T00:00:00',1);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(6,'title6','link6','2019-10-06T00:00:00',1);

INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(7,'title7','link7','2019-10-07T00:00:00',2);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(8,'title8','link8','2019-10-08T00:00:00',2);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(9,'title9','link9','2019-10-09T00:00:00',2);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(10,'title10','link10','2019-10-10T00:00:00',2);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(11,'title11','link11','2019-10-11T00:00:00',2);
INSERT INTO feed_article(id,title,link,published_date,feed_source_id) values(12,'title12','link12','2019-10-12T00:00:00',2);


INSERT INTO crew_article(id,title,content,author) values(1,'1기 게시글','1기 게시글',1);
INSERT INTO crew_article(id,title,content,author) values(2,'2기 게시글','2기 게시글',7);


INSERT INTO anonymous_article (id, title, content, signing_key, is_approved) VALUES (1, 'title', 'content', 'password', true);
INSERT INTO anonymous_article (id, title, content, signing_key, is_approved) VALUES (2, 'title', 'content', 'password', true);
INSERT INTO anonymous_article (id, title, content, signing_key, is_approved) VALUES (3, 'title', 'content', 'password', true);
INSERT INTO anonymous_article (id, title, content, signing_key, is_approved) VALUES (4, 'title', 'content', 'password', false);
INSERT INTO anonymous_article (id, title, content, signing_key, is_approved) VALUES (5, 'title', 'content', 'password', false);


INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('testChannel', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:01');
INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('testChannel2', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:02');
INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('testChannel3', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:03');
INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('전체-공지사항', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:01');
INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('전체-공지사항', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:02');
INSERT INTO slack_message (channel, author, content, download_link, download_link_from_slack, created_date) VALUES ('전체-공지사항', 'testAuthor', 'testContent', 'test.com', 'test2.com', '2019-10-09T00:00:03');