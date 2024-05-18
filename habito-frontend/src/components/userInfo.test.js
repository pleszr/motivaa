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

    it('should display loading when not initialized', () => {
        useKeycloak.mockReturnValue({ keycloak: mockKeycloak, initialized: false });
        render(<UserInfo />);
        expect(screen.getByText('Loading...')).toBeInTheDocument();
    });

    it('should display the user name if authenticated', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        expect(screen.getByText('Hello, JohnDoe')).toBeInTheDocument();
    });

    it('should show Login button when not authenticated', () => {
        mockKeycloak.authenticated = false;
        render(<UserInfo />);
        expect(screen.getByText('Login')).toBeInTheDocument();
    });

    it('should show Logout button when authenticated', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        expect(screen.getByText('Logout')).toBeInTheDocument();
    });

    it('should call login method when Login button is clicked', () => {
        mockKeycloak.authenticated = false;
        render(<UserInfo />);
        fireEvent.click(screen.getByText('Login'));
        expect(mockKeycloak.login).toHaveBeenCalled();
    });

    it('should call logout method when Logout button is clicked', () => {
        mockKeycloak.authenticated = true;
        render(<UserInfo />);
        fireEvent.click(screen.getByText('Logout'));
        expect(mockKeycloak.logout).toHaveBeenCalled();
    });
});
