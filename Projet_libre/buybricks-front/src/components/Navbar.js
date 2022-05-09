import { Link } from 'react-router-dom';

const Navbar = () => {
	return (
		<>
			<nav>
				<ol>
					<Link to="/">Home</Link>
					<Link to="/profile">Profile</Link>
					<li>Address</li>
					<li>Users</li>
					<Link to="/register">Register</Link>
				</ol>
			</nav>
		</>
	);
};

export default Navbar;
