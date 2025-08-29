/* global jest */
// Mock for stompjs
export const Stomp = {
  over: jest.fn(() => ({
    connect: jest.fn(),
    disconnect: jest.fn(),
    subscribe: jest.fn(),
    send: jest.fn(),
  })),
};
