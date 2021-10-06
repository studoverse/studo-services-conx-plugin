FROM conexus.tugraz.at/campusonline/co-quarkus:1-latest

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --chown=6666 target/quarkus-app/lib/ lib/
COPY --chown=6666 target/quarkus-app/*.jar .
COPY --chown=6666 target/quarkus-app/app/ app/
COPY --chown=6666 target/quarkus-app/quarkus/ quarkus/
