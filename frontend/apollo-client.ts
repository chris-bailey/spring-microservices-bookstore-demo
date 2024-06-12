// Apollo client for connecting to GraphQL endpoints.
import { ApolloClient, InMemoryCache } from '@apollo/client';

const client = new ApolloClient({
    uri: 'http://localhost:8080/api/graphql',
    cache: new InMemoryCache(),
});

export default client;
