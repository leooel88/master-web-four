import { Link } from 'react-router-dom';

const Navbar = () => {
	const profileLogin = () => {
		if (
			localStorage.getItem('userId') &&
			localStorage.getItem('authToken')
		) {
			return <Link to="/profile">Profile</Link>;
		} else {
			return <Link to="/login">Login</Link>;
		}
	};
	const registerLogin = () => {
		if (
			localStorage.getItem('userId') &&
			localStorage.getItem('authToken')
		) {
			return;
		} else {
			return (
				<li>
					<Link to="/register">Register</Link>
				</li>
			);
		}
	};

	return (
		<>
			<nav>
				<ul>
					<li>
						<Link to="/">Home</Link>
					</li>
					<li>{profileLogin()}</li>
					<li>Address</li>
					<li>Users</li>
					{registerLogin()}
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
