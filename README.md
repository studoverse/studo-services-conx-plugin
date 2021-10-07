# TODOS

* pu-views nach STUDO_SERVICES granten

* Tabellen als Liquibase Script

* Arbs Quarkus Code nach studo-services moven
  * POM merge
  * application yaml merge

* Authorisation
  * Scope studo-services.read scope anlegen
  * @RolesAllowed("studo-services.read")
  * Umstellung auf von service app auf hybrid
  * Tenant Resolver


# Studo Services

* https://trunkline.tugraz.at/trunk_dev/co/studo/services/app/
* http://localhost:8080/studo/services/api/role-examples?org-id=1
* http://localhost:8080/studo/services/api/role-examples/check-demo-1?org-id=1
* http://localhost:8080/studo/services/api/role-examples/check-demo-2?org-id=1

# CAMPUSonline Supersonic App Template

This project is the standard implementation to build an CAMPUSonline app (backend and frontend).

## Requirements

* Linux = tested with Ubuntu 20.04.2 LTS
* git = tested with 2.25.1
* Java = tested with openjdk 11.0.11
* Apache Maven = tested with 3.6.3
* node = tested with 14.17.4
* npm = tested with 6.14.14

In addition to this standard tools the CAMPUSonline commandline-installer is required
to be installed and configured on your developer machine.

* coinst = tested with 4.0.7

If you want to deploy your app you will need a docker-registry, to store your app docker image,
which should be deployd.

You can use https://hub.docker.com/ or you join the CAMPUSonline Community
(send an email to conx@tugraz.at) or you can host a docker registry by yourself.

If you join the CAMPUSonline Community you will get a username and password for the Community registry,
which is served under the url: docker.campusonline.community
You can login with the command

```
docker login docker.campusonline.community
```

### Generate Your App

To start your local app development, you can generate a supersonic app yourself by using a maven archetype.
The maven archetype is available in the maven repository
[https://conexus.tugraz.at/nexus/repository/campusonline-public-maven]()
and is also mirrored by the CAMPUSonline Community under
[https://nexus.campusonline.community/repository/campusonline-public-maven]()

To access these repositories add the following snippet to the profiles section in your ~/.m2/settings.xml
```
    <profile>
        <id>repos</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <repositories>
            <repository>
                <id>campusonline-public-maven</id>
                <name>CAMPUSonline Public Maven</name>
                <url>https://conexus.tugraz.at/nexus/repository/campusonline-public-maven</url>
            </repository>
        </repositories>
    </profile>
```

You can see, that we use the conexus.tugraz.at server from CAMPUSonline, to download maven artefacts.
The conexus.tugraz.at is only accessible from your university network,
and can typically not be used at home or any other location on the world.

As an alternative to access conexus.tugraz.at, you can use the CAMPUSonline Community Nexus.
To achieve this, you can add the nexus.campusonline.community as a mirror for the public CAMPUSonline repositories
in the settings.xml.

```
    <mirrors>
        <mirror>
            <id>campusonline-public-maven-mirror</id>
            <name>CAMPUSonline Public Maven</name>
            <url>https://nexus.campusonline.community/repository/campusonline-public-maven</url>
            <mirrorOf>campusonline-public-maven,co-central</mirrorOf>
        </mirror>
        <mirror>
            <id>campusonline-public-maven-plugins-mirror</id>
            <name>CAMPUSonline Public Maven</name>
            <url>https://nexus.campusonline.community/repository/campusonline-public-maven</url>
            <mirrorOf>campusonline-public-maven-plugins,co-central</mirrorOf>
        </mirror>
    </mirrors>
```

If you do not have a ~/.m2/settings.xml by now, you can download this one [setings.xml](settings.xml)
and store it in your ./m2 folder.

Now you can generate an app based on the co-supersonic-archetype with the following command:

```
mvn archetype:generate \
  -DarchetypeGroupId=at.campusonline.conx.api \
  -DarchetypeArtifactId=co-supersonic-archetype \
  -DarchetypeVersion=<CONX_PLATFORM_VERSION> \
  -DgroupId=<GROUP_ID> \
  -DartifactId=<ARTEFACT_ID> \
  -Dversion=0.0.1 \
  -DurlPrefix=<URL_PREFIX> \
  -DjdbcUrl=<JDBC_URL> \
  -DdbUser=<DB_USER> \
  -DdbUserPassword=<DB_USER_PASSWORD> \
  -DauthServerUrl=<AUTH_SERVER_URL> \
  -DpublicApiUrl=<PUBLIC_API_URL> \
  -DdockerRegistry=<DOCKER_REGISTRY>
```

You have to replace all <...> placeholder with your desired values.

For this section you will need the CAMPUSonline partner stack-prefix
to ensure a collision free naming over your code artefacts.
If you do not know your stack-prefix, consult the following page
[Stack-Prefix for local app development](https://gitlab.com/CoCommunity/co-examples/-/blob/develop/doc/stack-prefix-for-local-app-development.md).

Short descriptions of the placeholder:
<dl>
  <dt>CONX_PLATFORM_VERSION</dt>
  <dd>The desired version of the CoNX framework your app will based on.</dd>

  <dt>GROUP_ID</dt>
  <dd>This is the maven "groupId" for your project. e.g. de.rwth-aachen
  For mor information about group ids consult the following documentation:
  <a href="https://maven.apache.org/guides/mini/guide-naming-conventions.html">
    Guide to naming conventions on groupId, artifactId, and version
  </a>
  </dd>

  <dt>ARTIFACT_ID</dt>
  <dd>This is the maven "artifactId" for your project. e.g. rwth-supersonic.
  For mor information about artifact ids consult the following documentation:
  <a href="https://maven.apache.org/guides/mini/guide-naming-conventions.html">
    Guide to naming conventions on groupId, artifactId, and version
  </a>
  </dd>

  <dt>URL_PREFIX</dt>
  <dd>Every app needs to be served under a different base url.
  You can choose your base url by defining an URL_PREFIX.
  Use the <pre>[stack-prefix]/**</pre> pattern if you write a local app.
  e.g. rwth/example.
  </dd>

  <dt>JDBC_URL (only used in the .env file)</dt>
  <dd>This jdbc url is used to connect to your database. 
    e.g. jdbc:oracle:thin:@suri.tugraz.at:1521:trunkdev </dd>

  <dt>DB_USER</dt>
  <dd>This is the database user/schema which should be created for your app.
   This is the only database schema which your app will be allowed to access.
   Every app has its own schema. Use <pre>[STACK-PREFIX]_</pre> as prefix for your schema, if it is a local app.
   e.g. RWTH_EXAMPLE</dd>

  <dt>DB_USER_PASSWORD (only used in the .env file)</dt>
  <dd>The password for your database user/schema. You can use a plain or encrypted value during development</dd>

  <dt>AUTH_SERVER_URL (only used in the .env file)</dt>
  <dd>This the url to the openid connect authorisation server. 
    e.g. https://trunkline.tugraz.at/trunk_dev/co/public/sec/auth/realms/CAMPUSonline</dd>

  <dt>PUBLIC_API_URL (only used in the .env file)</dt>
  <dd>This is the base url to the public api of your CAMPUSonline instance.
    e.g. https://trunkline.tugraz.at/trunk_dev/co/public</dd>

  <dt>DOCKER_REGISTRY</dt>
  <dd>The url to your docker registry. e.g. docker.campusonline.community</dd>
</dl>

For example:
```
mvn archetype:generate \
  -DarchetypeGroupId=at.campusonline.conx.api \
  -DarchetypeArtifactId=co-supersonic-archetype \
  -DarchetypeVersion=CHANGE_ME \
  -DgroupId=de.rwth-aachen \
  -DartifactId=rwth-supersonic \
  -Dversion=0.0.1 \
  -DurlPrefix=rwth/supersonic \
  -DjdbcUrl=jdbc:oracle:thin:@suri.tugraz.at:1521:trunkdev \
  -DdbUser=RWTH_SUPERSONIC \
  -DdbUserPassword=CHANGE_ME \
  -DauthServerUrl=https://trunkline.tugraz.at/trunk_dev/co/public/sec/auth/realms/CAMPUSonline \
  -DpublicApiUrl=https://trunkline.tugraz.at/trunk_dev/co/public \
  -DdockerRegistry=docker.campusonline.community
```

## Setup

Once you have generated this project, you have to follow these steps ONCE, to run this app.

### Setup the environment and initialize git

Copy the .env-template-generated in the root folder of the project in a file called __.env__.
This file defines the complete environment of your app, like database schema, urls, and so on.
You can change the values in this file at runtime easily.

The .env file holds secret values like passwords for your database or OAuth2 clients.
So it is not manged by git (.gitignore).
You do not need the generated .env-template-generated after you have copied it,
so you can delete it.

It is strongly recommended to use git as version control system.
As a default .gitignore file you can use our template, or create your own.

```
cp .env-template-generated .env
rm .env-template-generated
mv .gitignore-template .gitignore
git init
git add .
git commit -m "Inital commit"
```

### Frontend Repository

Before you can build the whole app, we need to connect to an npm repository,
which serves the CAMPUSonline npm artefacts.

```
npm config set @campusonline:registry=https://conexus.tugraz.at/nexus/repository/campusonline-public-npm/
```

This should result in a ~/.npmrc file in your home folder
```
cat ~/.npmrc
```
containing the following string
```
@campusonline:registry=https://campusonline.tugraz.at/nexus/repository/conx-public-npm/
```

You have to be on the campus of your university to gain access to the conexus.tugraz.at.
So if you are anywhere else, you can also use the CAMPUSonline Community Nexus to download
CAMPUSonline npm artefacts. You can run this command to switch wo the Community Nexus URL:

```
npm config set @campusonline:registry=https://nexus.campusonline.community/repository/campusonline-public-npm/
```

Now the ~/.npmrc file should contain the following string
```
@campusonline:registry=https://nexus.campusonline.community/repository/campusonline-public-npm/
```

### Build the project without tests

At the end build your project without test execution:

```
mvn clean install -DskipTests
```

The tests are not ready to be executed at this point,
because we need to install a few things (database schema and oauth2 clients) first.

This will build the frontend and the backend.
The frontend build includes the installation
of the necessary node and npm version  for the build
and calls also "npm install" in the frontend folder.
Your local installed node and npm version should be
the same as defined in the pom.xml.

```
cat pom.xml | grep nodeVersion
cat pom.xml | grep npmVersion
```

You will notice changes in your git project.

```
git status
```

On the one hand a file called package-lock.json will be created.
It is recommended to commit this file to your git repository
for reproducible builds. It wil lock the version of each
npm dependency to a specific version.
On the other hand the model.ts file will be generated during the build.
This is the backend model which is automatically created into your frontend code.
The model.ts file includes all backend resource models and urls,
which are offered by the backend for the frontend.
It is recommended to commit this file also to your git repository.
If you do this, the frontend will always be compilable without the backend needs to be compiled,
and you will be able to track changes of the backend model in one place.

Now it is your decision to ignore these two file with a .gitignore entry
```
echo "frontend/meta/model.ts\npackage-lock.json" >> .gitignore
git add .
git commit -m "gitignore model and package-lock.json"
```
or if you want to track them in your git repository.
```
git add .
git commit -m "add frontend model and package-lock.json"
```

### Install database schema and OAuth2 Clients

Now you need to install the database schema and OAuth2 Clients.
This can easily be done with the CAMPUSonline Commandline Installer - coinst).

At first, you have to find the appropriate development machine, to install your items.

This can be done with
```
coinst list-connections
```

Pick the connection you want to use, replace the <CONNECTION> placeholder in the following commands and execute them.

```
coinst deploy <CONNECTION> IAM_RES target/deployment/iam_res/0001_app.client
coinst deploy <CONNECTION> IAM_RES target/deployment/iam_res/0002_app_user.client
coinst deploy <CONNECTION> SQL target/deployment/sql_scripts/0001_create_schema.sql
```

For example:
```
coinst deploy TRUNKdev IAM_RES target/deployment/iam_res/0001_app.client
coinst deploy TRUNKdev IAM_RES target/deployment/iam_res/0002_app_user.client
coinst deploy TRUNKdev SQL target/deployment/sql_scripts/0001_create_schema.sql
```

### Update your secrets in the .env file

There are to important secrets in your .env file, which has to be set properly:

#### ENV_QUARKUS_DATASOURCE_PASSWORD_ENCRYPTED ####

This is the database password for your user. This should be set correctly if you have specified it properly
during the processing of your mvn archetype:generate command. It does not need to be encrypted during development.

#### ENV_QUARKUS_OIDC_CLIENT_CREDENTIALS_SECRET ####

This is the client secret of your app client. It can be obtained via the keycloak admin GUI.
If you do not have access to your keycloak admin GUI, your Middle Tier Administrator,
should send you the client secret of your ```<app-client-id>```.

At first, you have to determine your ```<app-client-id>```. You can use the following command:

```
mvn help:evaluate -Dexpression=stack.oidc.app.client.id -q -DforceStdout
```

On the other hand, if you have admin access to your keycloak,
go to the administration page under:
[BASE_URL]/[DAD]/co/public/sec/auth/
(e.g. https://trunkline.tugraz.at/trunk_dev/co/public/sec/auth/).

* Login as administrator
* Select "Clients" in the menu.
* Select the ```<app-client-id>``` you are interested in
* Click on the credentials tab
* Copy the field "Secret"
* and paste it into your .env file as value for ENV_QUARKUS_OIDC_CLIENT_CREDENTIALS_SECRET

### Build the project

Now you should be ready to build your project with the tests.
The tests are full stack tests (from rest to database).

```
mvn clean install
```

## Start Development (frontend and backend)

Once you have build the project with _mvn clean install_
you can start development by using the following commands
from now on.

To start the backend:
```
mvn compile quarkus:dev
```

The backend will be served on
[http://localhost:8080/](http://localhost:8080/)
If you enter this url, the frontend of the supersonic app will be shown,
because the frontend resources are included in the backend as static resource.

__Attention:__ The login process will not work, because the login process
awaits a started angular frontend.

Next start the frontend by using npm:

```
cd frontend
npm start
```

The frontend will be served in development mode on
[http://localhost:4200/app/](http://localhost:4200/app/)

The app is ready to be used and you can do a user login now.

Your app is now running in development mode.
Edit frontend resources in the frontend folder,
or backend resources in the src folder,
and your application will be compiled and reloaded on the fly.

### Login process during development.

The supersonic app consists of three pages. Only the start page can be accessed anonymously.
The other pages (and of course the login button) will trigger a login process.

The login process for local development is not final and will be improved in future releases.
Read the [additional information about the login process](https://gitlab.com/CoCommunity/co-examples/-/blob/develop/doc/login-process.md)
to get an overview, how you can login into your CAMPUSonline development instance at the moment.
This procedure will be improved in the next releases.

### Compiling only the backend

If you only want to compile the backend without building the frontend,
use the command:

```
mvn clean compile
```

The frontend will not be build, because the frontend build will be done later in the
prepare-package phase.

### Typescript model generation

The typescript model will be generated in the folder frontend/meta.
This model is derived from the backend automatically.

To update the generated model it is sufficient to compile your project with:

```
mvn clean compile
```

## Other options to start your app

### Start frontend without backend

If you like to develop only the angular frontend without backend,
you can start the angular frontend by using static mock data.

You can enable this feature in your
[frontend/src/environments/environment.ts](frontend/src/environments/environment.ts).
Set ```useMocks: true``` and the json files in the folder frontend/mocks,
are used as datasource instead of the quarkus backend.

### Start Development (production mode)

If you do not like to start the angular proxy (npm start)
or if you only want to test your app in production mode,
start the backend with:

```
mvn quarkus:dev -Dquarkus.profile=prod
```

The app is now configured like in production.
This means you do not need to start the angular server.

You can use the app now under
[http://localhost:8080/](http://localhost:8080/)

### Starting your app in a docker container

If you like to test your application in a docker container,
the docker container has to be build.

This happens if you build your app with prod profile automatically.

```
mvn install -Dquarkus.profile=prod
```

Then use the following command to start the docker container with the environment defined by our .env file:

```
docker run -p 9080:8080/tcp --env-file .env conexus.tugraz.at/co-supersonic:<VERSION>
```

Replace ```<VERSION>``` placeholder with the current artefact version defined in your pom.xml.

You can list your project version with the command:
```
mvn help:evaluate -Dexpression=project.version -q -DforceStdout
```

Now you can test the app by under the url:
[http://localhost:9080/](http://localhost:9080/)

## Deploy your app

To deploy your app it is necessary to deploy a docker image into a docker registry.

The registry which will be used is defined in your pom.xml.

```
mvn help:evaluate -Dexpression=docker.registry -q -DforceStdout
```

It is important that you have write permissions to this docker registry.
You can check if you are already logged in by calling

```
cat ~/.docker/config.json
```

If the url of your registry is not listed you need to login with:

```
docker login <URL_TO_YOUR_REGISTRY>
```

### Upload your docker image

Now you can upload a production docker image to your registry with the command:

```
mvn clean deploy -Dquarkus.profile=prod
```

The docker container will be build and uploaded to the specified registry.

### Prepare the Middle Tier

Before you deploy your app, the guid yaml must be configured by your Middle Tier Administrator.

Basically your stack must be registered in the guid.yaml of your Middle Tier.
Also, the variables, which are needed by your stack have to be set in the guid.yaml

#### Prepare local development mode

To enable local development with a CAMPUSonline development instance
the stack co-auth and keycloak must allow redirects to your localhost machine
in the process of authentication and authorisation.

To configure the co-auth stack to allow localhost as a redirect url
the property ```backend_sepi_additional_redirect_urls``` has to be set to
```'http://localhost'```.

Example for the co-auth stack configuration:
```
co-auth:
    config:
        backend_db_introspection_secret: ${services.database.passwords.db_introspection_secret}
        backend_sepi_additional_redirect_urls: 'http://localhost'
        backend_db_password_co_core_auth:  ${services.database.passwords.default}
```

To configure your keycloak client for local development you have to
navigate to you keycloak client in the keycloak admin GUI.
Use the description in the section
[ENV_QUARKUS_OIDC_CLIENT_CREDENTIALS_SECRET](#keycloak-admin-gui)
to open your configuration for your ```<app-client-id>```
in the keycloak admin GUI.
In the settings tab of the client configuration
you will find a field called __Valid Redirect URIs__.
Add an additional valid redirect URI with the value
```http://localhost*```
to this configuration and press the save button.

#### Registration of your stack

In our case the configuration should look like that:

```
<STACK_ID>:
    config:
        backend_back_channel_client_secret: '<CLIENT_SECRET>'
        backend_db_password_co_supersonic: '<DATABASE_SECRET>'
```

The Middle Tier Administrator has to set the placeholder
```<STACK_ID>```, ```<CLIENT_SECRET>``` and ```<DATABASE_SECRET>``` accordingly.

The ```<STACK_ID>``` is typically your artefactId. You can also list your stack.id with the following command:

```
mvn help:evaluate -Dexpression=stack.id -q -DforceStdout
```

If your chosen docker registry is not anonymous accessible,
your Middle Tier Administrator needs to login the Middle Tier with

```
docker login <URL_TO_YOUR_REGISTRY>
```

### Deploy your app

Finally, you can deploy your app to your CAMPUSonline instance with the command:

```
coinst deploy <CONNECTION> DOCKER target/deployment/docker/<stack.id>.yml
```

The file name of your <stack.id>.yml has to correlate with
the name registered of the registered stack in the guid.yaml.

For example:
```
coinst deploy TRUNKdev DOCKER target/deployment/docker/co-supersonic.yml
```
