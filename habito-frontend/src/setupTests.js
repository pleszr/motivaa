// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom

// We are setting fetchMock as custom jest matcher for asserting on DOM nodes.
import '@testing-library/jest-dom';
import fetchMock from 'jest-fetch-mock';
fetchMock.enableMocks();
