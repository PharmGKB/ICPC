ARCHIVE_NAME = cpic_db_dump
DATED_NAME = icpc.$(shell date +'%Y%m%d').sql

archive:
	mkdir -p out
	pg_dump icpc -f out/${DATED_NAME} --no-privileges --no-owner
