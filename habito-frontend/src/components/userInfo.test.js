import React from 'react';
import { render, screen } from '@testing-library/react';
import { ReactKeycloakProvider, useKeycloak } from '@react-keycloak/web';
import UserInfo from './userInfo';

jest.mock('@react-keycloak/web', () => ({
  ReactKeycloakProvider: jest.fn(({ children }) => <div>{children}</div>),
  useKeycloak: jest.fn(),
}));

beforeEach(() => {
  jest.clearAllMocks();
});

test('renders user information when authenticated', () => {
  useKeycloak.mockReturnValue({
    keycloak: {
      authenticated: true,
      tokenParsed: { preferred_username: 'test-user' },
      login: jest.fn(),
      logout: jest.fn(),
    },
    initialized: true,
  });

  render(
    <ReactKeycloakProvider>
      <UserInfo />
    </ReactKeycloakProvider>
  );
  expect(screen.getByText('Hello, test-user')).toBeInTheDocument();
});

test('renders login button when not authenticated', () => {
  useKeycloak.mockReturnValue({
    keycloak: {
      authenticated: false,
      login: jest.fn(),
    },
    initialized: true,
  });

  render(
    <ReactKeycloakProvider>
      <UserInfo />
    </ReactKeycloakProvider>
  );
  expect(screen.getByText('Login')).toBeInTheDocument();
});





