import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
	const navigate = useNavigate();
	const login = async (json) => {
		const result = await axios.post('/authenticate', json);
		return result;
	};

	const handleLogin = async (event) => {
		event.preventDefault();
		const registerJson = {
			username: event.target.username.value,
			password: event.target.password.value,
		};
		const response = await login(registerJson);
		console.log(response);
		const { token, userId } = response.data;
		localStorage.setItem('authToken', `Bearer ${token}`);
		localStorage.setItem('userId', userId);

		navigate('/');
		window.location.reload(false);
	};

	return (
		<form onSubmit={handleLogin}>
			<label htmlFor="username">User name</label>
			<input
				type="text"
				id="username"
				name="username"
				placeholder="Your name"
				required
			/>

			<label htmlFor="password">Password</label>
			<input
				type="password"
				id="password"
				name="password"
				placeholder="Enter your password"
				required
			/>

			<input type="submit" value="Login" />
		</form>
	);
};

export default Login;
