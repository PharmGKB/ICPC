# ICPC MySQL Schema #

## Instructions ##

1. Get a MySQL instance running
2. _Import_ the `samples` table
3. _Populate_ the `samples` table
4. _Export_ your `samples` data

### Get a MySQL instance running ###

If you're running OS X, use [MAMP](http://www.mamp.info/en/index.html). _Tip: you only need MAMP, not MAMP Pro_

If you're running Windows, use [WAMP](http://www.wampserver.com/en/).

If you're running some flavor of Linux you probably know how to get a MySQL instance up and running with phpMyAdmin as a front-end to the DB.

If you're running some other OS, check [Wikipedia's list of *AMP packages](http://en.wikipedia.org/wiki/List_of_AMP_packages).

Once you install any of these packages you should have a local instance of [MySQL](http://www.phpmyadmin.net/home_page/index.php) running and a local instance of [phpMyAdmin](http://www.phpmyadmin.net/home_page/index.php). You can use any method of interacting with your MySQL instance but I'll be writing my instructions for phpMyAdmin.

### Import the samples table ###

Get to the front page for your local instance of phpMyAdmin.

Create a new database . It doesn't matter what it's called, but I used "ICPC".

Your ICPC database should be listed on the left hand side now, click on "ICPC".

Click on the "Import" tab. You should see a field labeled "Location of the text file". Click the "Choose File" button and navigate to the `ICPC_mysql_schema.sql` file included in this package. You can leave all other fields as they are. Click "Go" in the lower right.

Now you have an empty local copy of the ICPC schema. There should now be an instance of the `samples` table in your local database.

### Populate the samples table ###

Now you can populate the samples table however you like. All constraints for column values are specified including columns that cannot be blank and valid column values.

At the very least you can use the "Insert" tab on the `samples` table page to insert data. Otherwise, I would recommend batch importing from the excel template or some other batching mechanism.

### Export your samples data ###

Go to the page for your local instance of the `samples` table.

Click on the _Export_ tab. You can leave most option to their default values but there are a few you should change.

1. Uncheck the _Structure_ option.
2. Check the _Save as file_ option.
3. Fill in _File name template_ with your project name (e.g. "project1")

Click the _Go_ button at the bottom right. This will generate a `.sql` file. This file will contain all your sample data. Send this file back to PharmGKB and we will load it into the central consortia data store.
