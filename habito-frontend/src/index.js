// src/index.js
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js'; // Added .js extension
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './keycloak.js'; // Added .js extension

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <ReactKeycloakProvider authClient={keycloak} initOptions={{ onLoad: 'login-required' }}>
        <App />
    </ReactKeycloakProvider>
);


