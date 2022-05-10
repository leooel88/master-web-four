import { Link } from 'react-router-dom';

const Navbar = () => {
	return (
		<>
			<nav>
				<ul>
					<li>
						<Link to="/">Home</Link>
					</li>
					<li>
						<Link to="/profile">Profile</Link>
					</li>
					<li>Address</li>
					<li>Users</li>
					<li>
						<Link to="/register">Register</Link>
					</li>
					<li>
						<Link to="/brickCatalog">Catalog</Link>
					</li>
					<li>
						<Link to="/basket">Basket</Link>
					</li>
				</ul>
			</nav>
		</>
	);
};

export default Navbar;
