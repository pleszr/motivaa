// src/keycloak.js
import Keycloak from 'keycloak-js';


const keycloakConfig = {
  url: 'http://localhost:8080', // base url keycloak
  realm: 'motivaa', // realm name
  clientId: 'login', // ClientID, equals with the Client that you have created
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;