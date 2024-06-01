// src/keycloak.js
import Keycloak from 'keycloak-js';


const keycloakConfig = {
  url: 'http://localhost:8080', // base url keycloak
  realm: 'Motivaa', // realm name
  clientId: 'login',
  clientSecret: 'txKdPjPpwbiUuuvEYyO9Is6QZoUGbOMN',
  // ClientID, equals with the Client that you have created
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;