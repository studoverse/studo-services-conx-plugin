import { AppMockUris } from './app-mock-uris';

export const appMockUri = (name: keyof AppMockUris) => 'mocks' + name;
