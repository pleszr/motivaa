import React from 'react';
import withAuth from './auth/withAuth';
import UserInfo from './components/userInfo'; // Path of the UserINFO COMPONENT!

class App extends React.Component {
  state = {
    client: null,
    message: null,
    error: null
  };

  async componentDidMount() {
    await this.fetchHealthCheck();
    await this.fetchAuthenticatedMessage();
  }

  fetchHealthCheck = async () => {
    try {
      const response = await fetch('http://localhost:8093/apis/healthcheck');
      const body = await response.json();
      this.setState({ client: body.message });
    } catch (error) {
      console.error('Error during fetch:', error);
      this.setState({ error: 'Failed to fetch health check data.' });
    }
  }

  fetchAuthenticatedMessage = async () => {
    try {
      const response = await fetch('http://localhost:5000/api/message', {
        headers: {
          'Authorization': `Bearer ${this.props.keycloak.token}`,
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch the authenticated message.');
      }

      const data = await response.text();  // Assuming the server sends back plain text
      this.setState({ message: data });
    } catch (error) {
      console.error('Error during fetch:', error);
      this.setState({ error: 'Failed to fetch authenticated message.' });
    }
  }

  render() {
    const { client, message, error } = this.state;
    return (
        <div>
          <h1>API Messages</h1>
          <UserInfo /> {/* Displaying the user info component */}
          {error && <p>Error: {error}</p>}
          {client ? <p>Health Check: {client}</p> : <p>Loading health check...</p>}
          {message ? <p>Authenticated Message: {message}</p> : <p>Loading authenticated message...</p>}
        </div>
    );
  }
}

export default withAuth(App);
