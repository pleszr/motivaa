import React from 'react';
import keycloak from './keycloak';

const withAuth = (WrappedComponent) => {
  return class extends React.Component {
    componentDidMount() {
      keycloak.init({ onLoad: 'login-required' }).then(authenticated => {
        if (!authenticated) {
          window.location.reload();
        }
        // Continue with rendering or redirect
      });
    }

    render() {
      return <WrappedComponent {...this.props} />;
    }
  };
};

export default withAuth;
