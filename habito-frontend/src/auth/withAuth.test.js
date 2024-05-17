import React from 'react';
import { render, screen } from '@testing-library/react';
import { useKeycloak } from '@react-keycloak/web';
import withAuth from './withAuth';

jest.mock('@react-keycloak/web', () => ({
  useKeycloak: jest.fn(),
}));

const MockComponent = () => <div>Mock Component</div>;
const WrappedComponent = withAuth(MockComponent);

beforeEach(() => {
  jest.clearAllMocks();
});

test('renders loading indicator when Keycloak is not initialized', () => {
  useKeycloak.mockReturnValue({ keycloak: {}, initialized: false });
  render(<WrappedComponent />);
  expect(screen.getByText('Loading...')).toBeInTheDocument();
});

test('renders wrapped component when Keycloak is initialized', () => {
  useKeycloak.mockReturnValue({ keycloak: {}, initialized: true });
  render(<WrappedComponent />);
  expect(screen.getByText('Mock Component')).toBeInTheDocument();
});

