import React from 'react';
import './App.css';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom"
import Home from './Home';
import Profile from './user';
import ChangeRole from './ChangeRole';
function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route key="signin" path="/SimpleLogin/profile">
            <Profile />
          </Route>
          <Route key="signin" path="/SimpleLogin/changerole">
            <ChangeRole />
          </Route>
          <Route key="home" path="/SimpleLogin/">
            <Home />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}
export default App;
