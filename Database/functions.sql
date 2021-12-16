CREATE OR REPLACE FUNCTION get_estates(ip_page_number IN int, ip_estates_count IN int, op_all_rows OUT bigint, fhCursor OUT refcursor) AS $$
BEGIN
	
	OPEN fhCursor FOR
	SELECT e.*
	FROM estates e
	ORDER BY e.created_on desc
	LIMIT ip_estates_count
	OFFSET (ip_page_number - 1) * ip_estates_count;
	
	SELECT COUNT(e.estate_id)
	INTO op_all_rows
	FROM estates e;
END;
$$ LANGUAGE plpgsql;