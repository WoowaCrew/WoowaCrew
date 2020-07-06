INSERT INTO DEGREE (id, degree_number)
SELECT 1, 0
WHERE SELECT count(*) FROM DEGREE is 0;

INSERT INTO degree (id, degree_number)
SELECT 2, 1
WHERE SELECT count(*) FROM DEGREE is 1;