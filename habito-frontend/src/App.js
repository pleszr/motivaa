import React, { Component } from 'react';
import withAuth from './auth/withAuth';

class App extends React.Component {
  state = {
    client: null,
  };

  async componentDidMount() {
    try {
      const response = await fetch('http://localhost:8093/apis/healthcheck');
      const body = await response.json();
      this.setState({client: body.message});
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }

  render() {
    const { client } = this.state;

    return (
      <div>
        {client ? <p>{client}</p> : <p>Loading...</p>}
      </div>
    );
  }
}

export default App;

