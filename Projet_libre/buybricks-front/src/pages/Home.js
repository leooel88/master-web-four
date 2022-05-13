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
		<div className="flex flex-col grid place-items-center h-screen">
			<img src={require('./title.gif')}></img>
			<iframe src="https://giphy.com/embed/26CaNjBJS7fIW4NFe" width="480" height="480" frameBorder="0" class="giphy-embed" allowFullScreen></iframe>
		</div>
	);
};

export default Home;
