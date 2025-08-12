# PostgreSQL Local Setup (Linux, apt)

Replace placeholders <...> with your actual values.

## Steps

1. `sudo apt update -y`
2. `sudo apt install -y postgresql postgresql-contrib`
3. `sudo -u postgres psql`
4. `CREATE ROLE <username> LOGIN CREATEDB PASSWORD '<password>';`
5. `CREATE DATABASE <username> OWNER <username>;`
6. `CREATE ROLE <appname> LOGIN PASSWORD '<appname>';`
7. `CREATE DATABASE <dbname> OWNER <appname> ENCODING 'UTF8';`
8. `ALTER ROLE <username> WITH CREATEROLE CREATEDB;`
9. `\q`
10. `psql`

## Connection test (app user)
`psql -h localhost -U <appname> -d <dbname> -W`

## etc
`sudo cp .devcontainer/docker/postgres/conf/postgresql.dev.conf /etc/postgresql/<version>/main/`

`sudo chmod 644 /etc/postgresql/<version>/main/postgresql.dev.conf`


```postgresql.conf
include_if_exists = 'postgresql.dev.conf'
```

`sudo systemctl restart postgresql`