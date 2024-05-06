import React from 'react';
import withAuth from './auth/withAuth';

class App extends React.Component {
  state = {
    client: null,
    error: null
  };

  async componentDidMount() {
    try {
      const response = await fetch('http://localhost:8093/apis/healthcheck');
      const body = await response.json();
      this.setState({ client: body.message });
    } catch (error) {
      console.error('Error during fetch:', error);
      this.setState({ error: 'Failed to fetch data.' });
    }
  }

  render() {
    const { client, error } = this.state;
    if (error) return <p>Error: {error}</p>;
    return <div>{client ? <p>{client}</p> : <p>Loading...</p>}</div>;
  }
}


export default withAuth(App);


