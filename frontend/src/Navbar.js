import { Link } from "react-router-dom";
const Navbar = () => {
    return (
        <div className="navbar">
            <h2>Social Networking App</h2>
            <div className="links">
                <Link to="/">Home</Link>
                <Link to="/Login">Login</Link>
                <Link to="/Register">Register</Link>
                <Link to="/Feed">Feed</Link>
            </div>
        </div>
    );
}

export default Navbar; //