import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { ReactKeycloakProvider, useKeycloak } from '@react-keycloak/web';
import App from './App';
import fetchMock from 'jest-fetch-mock';

jest.mock('@react-keycloak/web', () => ({
  ReactKeycloakProvider: jest.fn(({ children }) => <div>{children}</div>),
  useKeycloak: jest.fn(),
}));

fetchMock.enableMocks();

beforeEach(() => {
  jest.clearAllMocks();
  fetchMock.resetMocks();

  useKeycloak.mockReturnValue({
    keycloak: { token: 'test-token' },
    initialized: true,
  });
});

test('renders loading state initially', () => {
  render(
    <ReactKeycloakProvider>
      <App />
    </ReactKeycloakProvider>
  );
  expect(screen.getByText('Loading health check...')).toBeInTheDocument();
  expect(screen.getByText('Loading authenticated message...')).toBeInTheDocument();
});

test('fetches and displays health check data', async () => {
  fetchMock.mockResponses(
    [JSON.stringify({ healthStatus: 'Healthy' }), { status: 200 }],
    ['Authenticated message', { status: 200 }]
  );

  render(
    <ReactKeycloakProvider>
      <App />
    </ReactKeycloakProvider>
  );

  await waitFor(() => expect(screen.getByText('Health Check: Healthy')).toBeInTheDocument());
  await waitFor(() => expect(screen.getByText('Authenticated Message: Authenticated message')).toBeInTheDocument());
});

test('handles fetch errors gracefully', async () => {
  fetchMock.mockResponses(
    () => Promise.reject(new Error('Failed to fetch health check data')),
    () => Promise.reject(new Error('Failed to fetch authenticated message'))
  );

  render(
    <ReactKeycloakProvider>
      <App />
    </ReactKeycloakProvider>
  );

  await waitFor(() => expect(screen.getByText('Failed to fetch health check data.')).toBeInTheDocument());
  await waitFor(() => expect(screen.getByText('Failed to fetch authenticated message.')).toBeInTheDocument());
});







