import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import fetchMock from 'jest-fetch-mock';
import App from './App';

// Enable fetch mocking
fetchMock.enableMocks();

jest.mock('./auth/withAuth', () => {
    const React = require('react');
    return (Component) => (props) =>
        React.createElement(Component, {
            ...props,
            keycloak: { token: 'mock-token' },
        });
});

jest.mock('./components/userInfo', () => {
    const React = require('react');
    return () => React.createElement('div', null, 'UserInfo Component');
});

describe('App', () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    afterEach(() => {
        jest.resetModules(); // Reset modules after each test to avoid conflicts
    });

    // First-test to check if the App component renders
    it('should display loading messages initially', () => {
        render(<App />);
        expect(screen.getByText('Loading health check...')).toBeInTheDocument();
        expect(screen.getByText('Loading authenticated message...')).toBeInTheDocument();
    });
    // Second-test check if the API messages are displayed
    it('should display the API messages once fetched', async () => {
        fetchMock.mockResponses(
            [JSON.stringify({ healthStatus: 'ok', customMessage: 'cica' }), { status: 200 }],
            ['Only authenticated users can see this.', { status: 200 }]
        );

        render(<App />);

        expect(screen.getByText('Loading health check...')).toBeInTheDocument();
        expect(screen.getByText('Loading authenticated message...')).toBeInTheDocument();

        await waitFor(() => expect(screen.getByText('Health Check: ok')).toBeInTheDocument());
        await waitFor(() =>
            expect(screen.getByText('Authenticated Message: Only authenticated users can see this.')).toBeInTheDocument()
        );
    });
    // Third-test to check if the error message is displayed if the health check fails
    it('should display an error message if health check fails', async () => {
        fetchMock.mockResponses(
            [new Error('Failed to fetch health check'), { status: 500 }],
            ['Only authenticated users can see this.', { status: 200 }]
        );

        render(<App />);

        await waitFor(() =>
            expect(screen.getByText((content, element) => {
                return element.tagName.toLowerCase() === 'p' && content.includes('Failed to fetch health check data.');
            })).toBeInTheDocument()
        );
    });
    // Fourth-test to check if the error message is displayed if the authenticated message fails
    it('should display an error message if fetching authenticated message fails', async () => {
        fetchMock.mockResponses(
            [JSON.stringify({ healthStatus: 'ok', customMessage: 'cica' }), { status: 200 }],
            [new Error('Failed to fetch authenticated message'), { status: 500 }]
        );

        render(<App />);

        await waitFor(() => expect(screen.getByText('Health Check: ok')).toBeInTheDocument());
        await waitFor(() =>
            expect(screen.getByText((content, element) => {
                return element.tagName.toLowerCase() === 'p' && content.includes('Failed to fetch authenticated message.');
            })).toBeInTheDocument()
        );
    });
    // Fifth-test to check if the UserInfo component is rendered
    it('should render UserInfo component', () => {
        render(<App />);
        expect(screen.getByText('UserInfo Component')).toBeInTheDocument();
    });
});

