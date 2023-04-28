# Studo Services

## Installation

### Process resources and prepare `deployment` folder (Done by a Studo developer)
```
mvn process-resources
```
Then zip `target/deployment` and send it to the CampusOnline administrator at the university.

### Install iam_res
```
coinst deploy TRUNKdev IAM_RES deployment/iam_res/studo-services.read.scope.json
coinst deploy TRUNKdev IAM_RES deployment/iam_res/studo-services-all.read.scope.json
coinst deploy TRUNKdev IAM_RES deployment/iam_res/studo-services-app.client.json
coinst deploy TRUNKdev IAM_RES deployment/iam_res/studo-services-app-user.client.json
```
Find the `studo-services-app` user and copy it's client-secret, we need it for the guid.yaml
Set "Valid redirect URIs" to your Flow's URL.

### Install sql_scripts
```
coinst deploy TRUNKdev SQL deployment/sql_scripts/0001_create_schema.sql
coinst deploy TRUNKdev SQL deployment/sql_scripts/0002_grants.sql
```
Generate a random password for the newly created `STUDO_SERVICES` database user.

### Set env vars in guid.yaml (big yaml with all docker containers, may have different name on installation)
``` 
studo-services:
    config:
        back_channel_client_secret: 'studo-services-app client secret'
        database_password: 'STUDO_SERVICES database password'
        backend_studo_service_token_secret: 'generate_here_a_second_uuid_and_send_to_studo'
        backend_studo_service_dal_base_url: 'DAL URL (for example https://dal-demo.campus-qr.at/admin)'
        backend_studo_service_debug_mode: false
```

### Install docker
This command deploys the latest commit working on the `snapshot` branch.
The docker image is already pre-built by CampusOnline's CI.
```
coinst deploy TRUNKdev DOCKER target/deployment/docker/studo-services.yml
```

## Technical Infos for local development

### Setup
Use the [SUPERSONIC.md](SUPERSONIC.md) as a setup reference

You have to add the following property to your .env file:
ENV_STUDO_SERVICE_TOKEN_SECRET=mysecretmysecretmysecretmysecretmysecretmysecret

### Auth
* If you like to implement a pure rest service, you have to use the @UserSessionDisabled annotation
  and use a /rest Path Prefix
* If you like to prevent anonymous access use the @RolesAllowed("studo-services.read") annotation

### Examples
* [Example REST Service with and without security annotations](src/main/java/com/studo/services/attendance/rest/AttendanceTestRestService.java)
* [Redirect JWT example](src/main/java/com/studo/services/attendance/rest/AttendanceRedirectRestService.java)

## Example APIs

* https://trunkline.tugraz.at/trunk_dev/co/studo/services/app/
* http://localhost:8080/studo/services/api/role-examples?org-id=1
* http://localhost:8080/studo/services/api/role-examples/check-demo-1?org-id=1
* http://localhost:8080/studo/services/api/role-examples/check-demo-2?org-id=1

### Troubleshooting

#### Gradle dependencies can't be fetched from CampusOnline Nexus

The CampusOnline Nexus is only accessible form their internal VPN, so add the correct mirrors to your Maven settings.
 * Make sure that you have a local maven installation and `~/.m2/settings.xml` exists
 * Copy the contents from the project's `settings.xml` into `~/.m2/settings.xml`

#### Directly redeploy only the `studo-services` docker container
```
coinst redeploy QC02 studo-services
```
