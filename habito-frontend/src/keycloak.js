// src/keycloak.js
import Keycloak from 'keycloak-js';

const keycloakConfig = {
  url: 'http://localhost:8080',
  realm: 'habito',
  clientId: 'login',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;




