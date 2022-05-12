import { Link } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
	const navigate = useNavigate();

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

	const handleAdminRoute = async (event) => {
		let result = '';
		try {
			result = await axios.get('/user/isadmin', {
				headers: {
					Authorization: localStorage.getItem('authToken'),
				},
			});
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			navigate('/brickEdition');
		} else {
			console.log('USER IS NOT ADMIN');
			return;
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
					<li>
						<Link to="/orders">Orders</Link>
					</li>
					<li>
						<button onClick={handleAdminRoute}>BrickEdition</button>
					</li>
				</ul>
			</nav>
		</>
	);
};

export default Navbar;
