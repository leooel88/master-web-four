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
		<div class="flex flex-col grid place-items-center h-screen">
			<form
				class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
				onSubmit={handleLogin}
			>
				<div class="mb-4">
					<label
						class="block text-gray-700 text-sm font-bold mb-2"
						htmlFor="username"
					>
						User name
					</label>
					<input
						class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						type="text"
						id="username"
						name="username"
						placeholder="Your name"
						required
					/>
				</div>

				<div class="mb-4">
					<label
						class="block text-gray-700 text-sm font-bold mb-2"
						htmlFor="password"
					>
						Password
					</label>
					<input
						class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						type="password"
						id="password"
						name="password"
						placeholder="Enter your password"
						required
					/>
				</div>

				<div class="flex items-center justify-between">
					<input
						class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
						type="submit"
						value="Login"
					/>
				</div>
			</form>
		</div>
	);
};

export default Login;
