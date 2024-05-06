import React from 'react';
import {useKeycloak} from '@react-keycloak/web';

const withAuth = (WrappedComponent) => {
  return (props) => {
    const {keycloak, initialized} = useKeycloak();

    if (!initialized || !keycloak.authenticated) {
      return <div>Loading or not authenticated...</div>;
    }

    return <WrappedComponent {...props} />;
  };
};

export default withAuth;

