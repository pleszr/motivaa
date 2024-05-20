import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { useKeycloak } from '@react-keycloak/web';
import UserInfo from './userInfo';

jest.mock('@react-keycloak/web');

describe('UserInfo', () => {
    const mockKeycloak = {
        tokenParsed: { preferred_username: 'JohnDoe' },
        authenticated: false,
        login: jest.fn(),
        logout: jest.fn(),
    };

    beforeEach(() => {
        useKeycloak.mockReturnValue({ keycloak: mockKeycloak, initialized: true });
    });
    // First-test to check if the UserInfo component renders
    it('should display loading when not initialized', () => {
        useKeycloak.mockReturnValue({ keycloak: mockKeycloak, initialized: false });
        render(<UserInfo />);
        expect(screen.getByText('Loading...')).toBeInTheDocument();
    });
    // Second-test to check if the user name is displayed if authenticated
    it('should display the user name if authenticated', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        expect(screen.getByText('Hello, JohnDoe')).toBeInTheDocument();
    });
    // Third-test to check if the Login button is displayed if not authenticated
    it('should show Login button when not authenticated', () => {
        mockKeycloak.authenticated = false;
        render(<UserInfo />);
        expect(screen.getByText('Login')).toBeInTheDocument();
    });
    // Fourth-test to check if the Logout button is displayed if authenticated
    it('should show Logout button when authenticated', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        expect(screen.getByText('Logout')).toBeInTheDocument();
    });
    // Fifth-test to check if the login method is called when the Login button is clicked
    it('should call login method when Login button is clicked', () => {
        mockKeycloak.authenticated = false;
        render(<UserInfo />);
        fireEvent.click(screen.getByText('Login'));
        expect(mockKeycloak.login).toHaveBeenCalled();
    });
    // Sixth-test to check if the logout method is called when the Logout button is clicked
    it('should call logout method when Logout button is clicked', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        fireEvent.click(screen.getByText('Logout'));
        expect(mockKeycloak.logout).toHaveBeenCalled();
    });
});
