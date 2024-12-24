import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import Navbar from './Navbar';
import Feed from './components/Feed';
import SearchResults from "./components/Search/SearchResults";
import UserProfile from './components/Search/UserProfile';


const App = () => {

    return (
        <Router>
            <div className="App">
                <Navbar />

                <Routes>
                    <Route path="/Feed" element={<Feed />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/" element=
                        {
                            <div style={{ textAlign: "center" }}>
                                <br></br>
                                <h2>Welcome to the Social Network!</h2>
                                <br></br>
                                <p>Please <Link to="/login" style={{ textDecoration: "underline", color: "blue" }}>Login</Link> or <Link to="/register" style={{ textDecoration: "underline", color: "blue" }}>Register</Link> on the top right!
                                </p>
                            </div>} />
                    <Route path="/search" element={<SearchResults />} />
                    <Route path="/user/:username" element={<UserProfile />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
