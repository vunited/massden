#MiscScripts contains scripts that accomplish something

##addyearfromfilenametoendofline.sh
Takes a year from the file name and adds the year to the end of each line.  The "-i" of sed is the flag to edit the file in place. "BASH_REMATCH[1]" is the the first subpattern assigned from the regex.

##createCSVWithCommasInAField.py
Python script to create a CSV with fields 0 and 2 having only letters and numbers and field 1 having punctuation in it.

##createFilesForFlume.py
Python script to create a file every 2 minutes using the same code as createCSVWithCommasInAField.py.  Note that file_paths is set to "./". Change the to alter what path the files are written too.

##dateToSQLDate.py
Reads a csv from https://archive.ics.uci.edu/ml/datasets/ISTANBUL+STOCK+EXCHANGE and changes the dates via Pandas Dataframes.

##hbaseDoctorsDataInsert.py
Python script to read a CSV file of https://data.medicare.gov/data/physician-compare doctors data and insert each row into HBase via os.system() command to echo a command into the hbase shell.  This is very slow.

##hdpSandboxHBaseStart.sh
Uses Ambari's REST API to stop Oozie, Atlas, and Hive and starts HBase.  Only tested on HDP 2.3 Sandbox.

##insertStockDataToPhoenix.py
Inserts Stock data from Pandas get_data_yahoo into Phoenix/HBase via JayDeBeAPI.  Note that the commit() from the connection is important as it pushs the inserts in.  Also, no semicolon is used for the "upsert".

##phoenixDoctors.sql
Sample create tables of creating a Phoenix table over an existing HBase table with a lowercase table name, lowercase column families, and lowercase columns.  Note that there are also 2 column families in the existing HBase table (personal and medical).  Note the quotes only need to be used if the table name, column family, or column is lowercase.  Also, the field defined in the sql is columnFamily.column.

##pyPhoenixJDBC.py
Simple connection script for Python to Phoenix and then puts the rows and columns into a pandas DataFrame. Connection code from https://gist.github.com/randerzander/3fd189409cab970156b3 using JayDeBeApi.

##PySparkToPhoenix.py
Simple script to connect Phoenix to Spark via Python.  This is a Python version of https://phoenix.apache.org/phoenix_spark.html - Load as a DataFrame using the Data Source API.

##pythonUpgradeScript.sh
Script for upgrading to Python 2.7 while keeping Python 2.6 in place.

##stocksYahoo.sql
An SQL script to create a table for the stock data from yahoo (and pandas get_data_yahoo service).  Built sepcifically for Apache Phoenix.
