import axios from 'axios';

const Register = () => {
	const register = async (json) => {
		const result = await axios.post('/register', json);
		return result;
	};

	const handleRegister = async (event) => {
		event.preventDefault();
		const registerJson = {
			username: event.target.username.value,
			password: event.target.password.value,
		};
		const registerResult = await register(registerJson);

		let result = await axios.post('/basket', {
			user_id: registerResult.data.userId,
		});
		console.log(result);
		return result;
	};

	return (
		<form onSubmit={handleRegister}>
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

			<input type="submit" value="Register" />
		</form>
	);
};

export default Register;
