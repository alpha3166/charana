# Steps to open the CharAna page

## Clone

- `git clone https://github.com/alpha3166/charana`
- `cd charana/local`

## Prepare development environment

- `docker-compose up -d`
- `docker-compose exec charana bash`

## Build

- `cd /mnt/charana-core`
- `mvn install`
- `cd /mnt/charana-war`
- `mvn package`

## Deploy

- `asadmin start-domain`
- `asadmin deploy --contextroot charana target/charana-war-*.war`

## Browse

- Open the [CharAna page](http://localhost:8080/charana/faces/index.xhtml)

## Stop

- `exit`
- `docker-compose stop`

## Start again

- `docker-compose up -d`
- `docker-compose exec charana bash`
- `asadmin start-domain`
- Open the [CharAna page](http://localhost:8080/charana/faces/index.xhtml)
