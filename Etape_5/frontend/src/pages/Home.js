import axios from 'axios';

const Home = () => {
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
		const { token } = response.data;
		localStorage.setItem('authToken', `Bearer ${token}`);
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

export default Home;
