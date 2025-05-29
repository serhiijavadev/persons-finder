INSERT INTO persons (id, name)
SELECT i, 'Person_' || i
FROM generate_series(1, 50000000) AS s(i);

INSERT INTO locations (id, latitude, longitude)
SELECT i,
       1.0 + random() * 1.0,  -- latitude near Singapore
       103.5 + random() * 1.0 -- longitude near Singapore
FROM generate_series(1, 50000000) AS s(i);


EXPLAIN ANALYZE
SELECT *
FROM locations
WHERE latitude BETWEEN 1.0 AND 2.0
  AND longitude BETWEEN 103.5 AND 104.5;


CREATE INDEX idx_lat ON locations(latitude);
CREATE INDEX idx_lat_lon ON locations(latitude, longitude);


SELECT indexname, indexdef
FROM pg_indexes
WHERE tablename = 'locations';


SET enable_seqscan = OFF;

SET enable_seqscan = ON;