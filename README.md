[![DOI](https://zenodo.org/badge/6285/PharmGKB/ICPC.png)](http://dx.doi.org/10.5281/zenodo.11859)

# ICPC Data Processing

This code will read in Excel data submitted by project participants, store it in a PostgreSQL database, and export information into defined reports. This project was developed in IntelliJ IDEA and has configuration files checked in for running easily in that envirionment.


## Get a PostgreSQL instance running

If you're running OS X, use [Postgres.app](http://postgresapp.com).

If you're running Windows, use the [official PostgreSQL installer](http://www.postgresql.org/download/windows/).

If you're running some flavor of Linux you probably know how to get a PostgreSQL instance up and running so it's left as an exercise to the reader.


## Database configuration

Update [hibernate.cfg.xml](/conf/hibernate.cfg.xml) to make sure it reflects your database setting, especially hostname, user, and password.

Run the [DDL script](/conf/db.schema.ddl) to setup database tables, indexes, and constraints.


## Load Data

Data is expected to come in as `.xlsx` files and to follow the [default template](/resources/org/pharmgkb/ICPC_Submission_Template.xlsx) included in this codebase.

You can load data with the [cl.TemplateParser](/src/cl/TemplateParser.java) class. Run it with the `-d` flag and value to load a whole directory of `.xlsx` files or with the `-f` flag and value to load a single `.xlsx` file. While loading watch the console and the log for data warnings and errors. Fix these directed.

You might also need to load extra DNA information into the system with the [cl.DnaParser](/src/cl/DnaParser.java) class. This only takes a single file with the `-f` flag and value.


## Report the Data

You can dump all processed data as one big, combined file using the [cl.DnaParser](/src/cl/ReportGeneratorCLI.java) class. Set the `-f` flag to filepath you want the report written to (don't forget to end the filename with `.xlsx`). 

*Tip:* You might need to have a pretty big heap to run this, `-Xmx3g` is recommended.


## Database Administration

To export (and compress) a copy of the db, replace <username> with your username

    pg_dump -U <username> -F p icpc | gzip > icpc.sql.gz

To import a copy the db

    psql -U <username> -d icpc -a -f icpc.sql
