import withAuth from './auth/withAuth.jsx'; 
import UserInfo from './components/userInfo.jsx'; 
import Header from './components/Header.jsx';
import ProgressBar from './components/ProgressBar.jsx';
import Dashboard from './components/Dashboard/Dashboard.jsx';
import { useState } from 'react';
import './index.css';

export default function App() {
  const [activeMenu, setActiveMenu] = useState("home");
  return (
    <main className="flex flex-col gap-12"> 
      <Header active={activeMenu} setActive={setActiveMenu} />
      <ProgressBar />
      <Dashboard />
    </main>
  )
}


// class App extends React.Component {
//   state = {
//     client: null,
//     message: null,
//     error: null,
//   };

//   async componentDidMount() {
//     await this.fetchHealthCheck();
//     await this.fetchAuthenticatedMessage();
//   }

//   fetchHealthCheck = async () => {
//     try {
//       const response = await fetch('http://localhost:8093/apis/healthcheck');
//       const body = await response.json();
//       this.setState({ client: body.healthStatus });
//     } catch (error) {
//       console.error('Error during fetch:', error);
//       this.setState({ error: 'Failed to fetch health check data.' });
//     }
//   };

//   fetchAuthenticatedMessage = async () => {
//     try {
//       const response = await fetch('http://localhost:8093/apis/authTest', {
//         headers: {
//           Authorization: `Bearer ${this.props.keycloak.token}`,
//         },
//       });

//       if (!response.ok) {
//         throw new Error('Failed to fetch the authenticated message.');
//       }

//       const data = await response.text(); 
//       this.setState({ message: data });
//     } catch (error) {
//       console.error('Error during fetch:', error);
//       this.setState({ error: 'Failed to fetch authenticated message.' });
//     }
//   };



//   render() {
//     const { client, message, error } = this.state;
//     return (
//       <div>
//         <h1>API Messages</h1>
//         <UserInfo /> {/* Displaying the user info component */}
//         {error && <p>Error: {error}</p>}
//         {client ? <p>Health Check: {client}</p> : <p>Loading health check...</p>}
//         {message ? <p>Authenticated Message: {message}</p> : <p>Loading authenticated message...</p>}
//       </div>
//     );
//   }
// }

// export default withAuth(App);