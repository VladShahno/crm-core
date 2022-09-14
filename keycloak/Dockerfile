FROM jboss/keycloak:16.1.1
COPY ./realm-import /opt/jboss/keycloak/imports
COPY --chown=jboss user-import /opt/jboss/keycloak/standalone/configuration/
EXPOSE 8080