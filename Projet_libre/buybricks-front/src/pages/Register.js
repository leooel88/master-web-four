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
		<div class='flex flex-col grid place-items-center h-screen'>
		<form onSubmit={handleRegister} class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
			<div class="mb-4">
			<label htmlFor="username" class="block text-gray-700 text-sm font-bold mb-2">User name</label>
			<input
				class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
				type="text"
				id="username"
				name="username"
				placeholder="Your name"
				required
			/>
			</div>
			<div class="mb-6">
			<label htmlFor="password" class="block text-gray-700 text-sm font-bold mb-2">Password</label>
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
			<input type="submit" value="Register" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"/>
			</div>
		</form>
		</div>
	);
};

export default Register;
