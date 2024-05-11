// src/components/UserInfo.js
import React from 'react';
import { useKeycloak } from '@react-keycloak/web';

function UserInfo() {
    const { keycloak, initialized } = useKeycloak();

    if (!initialized) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {keycloak.authenticated ? (
                <div>
                    <p>Hello, {keycloak.tokenParsed.preferred_username}</p>
                    <button onClick={() => keycloak.logout()}>Logout</button>
                </div>
            ) : (
                <button onClick={() => keycloak.login()}>Login</button>
            )}
        </div>
    );
}

export default UserInfo;
