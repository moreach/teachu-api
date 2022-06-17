# teachu-api
Api for TeachU 

## Test data
User: teacher@test.ch
Password: test
Auth: teacher  
User: student@test.ch
Password: test
Auth: student  
User: parent@test.ch
Password: test
Auth: parent  
User: parent2@test.ch
Password: test
Auth: parent2  
User: student2@test.ch
Password: test
Auth: student2

## Docker
Run in docker containers with `docker-compose up` in the "./docker" directory. 

### Database data loading
When `docker-compose up` is run for the first time the database executes the `teachu.sql` and then the `teachu-data.sql` scripts. Unfortunatly this process makes the db unavailable for connections and it takes longer then the api to start. So by the time the api searches for the db, the db is still running these scripts. This unfortunatly leads to a connection error in the first start. I could not find a better solution then to just wait for the db to finish and then restart the containers. 

### Handle changes in the local Repo
Since docker basically caches everything, changes in the local git repo won't show directly after `docker-compose up`. Here is a list of steps that have to be done after changes to the repo.  

(in the `./docker` dir)
1. `docker-compose down` -- always
2. `docker volume rm docker_teachu-data` -- only if the default data has to be reloaded (changes in the docs dir)
3. `docker-compose build --no-cache` -- only if source has changed (runs `mvn clean install`)
4. 2x `docker-compose up` -- to start the containers again (explained why twice in chapter 'Database data loading')
