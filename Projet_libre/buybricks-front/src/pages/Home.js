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
		const { token, userId } = response.data;
		localStorage.setItem('authToken', `Bearer ${token}`);
		localStorage.setItem('userId', userId);
	};

	return (
		<div className="home-wrapper">
			<p>HOME</p>
		</div>
	);
};

export default Home;
