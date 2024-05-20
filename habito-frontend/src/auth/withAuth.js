// src/auth/withAuth.js
import React from 'react';
import { useKeycloak } from '@react-keycloak/web';

function withAuth(WrappedComponent) {
  return function WrappedComponentWithAuth(props) {
    const { keycloak, initialized } = useKeycloak();

    if (!initialized) {
      return <div>Loading...</div>; // Or some loading indicator
    }

    return <WrappedComponent {...props} keycloak={keycloak} />;
  };
}

export default withAuth;