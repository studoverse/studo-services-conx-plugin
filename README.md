# Studo Services

## Installation

### Process ressources
```
mvn process-resources
```

### Install iam_res
```
coinst deploy TRUNKdev IAM_RES target/deployment/iam_res/0001_studo-service.read.scope
coinst deploy TRUNKdev IAM_RES target/deployment/iam_res/0002_app_backend.client
coinst deploy TRUNKdev IAM_RES target/deployment/iam_res/0003_app_user.client
```

### Install sql_scripts
```
coinst deploy TRUNKdev SQL target/deployment/sql_scripts/0001_create_schema.sql
coinst deploy TRUNKdev SQL target/deployment/sql_scripts/0002_grants.sql
```

### Set env vars (guid.yaml ?)
``` 
studo-services:
    config:
        backend_back_channel_client_secret: 'generate_here_a_first_uuid_and_send_to_studo'
        backend_db_password_STUDO_SERVICES:  ${services.database.passwords.default}
        backend_studo_service_token_secret: 'generate_here_a_second_uuid_and_send_to_studo'
        backend_studo_service_dal_base_url: 'https://dal-demo.campus-qr.at/admin'
```

### Install docker
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