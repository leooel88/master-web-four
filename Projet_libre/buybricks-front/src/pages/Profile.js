import axios from 'axios';
import React from 'react';
import { Navigate, useNavigate } from 'react-router-dom';

const Profile = () => {
	const navigate = useNavigate();
	const [username, setUsername] = React.useState('');
	const [role, setRole] = React.useState('');
	const [id, setId] = React.useState('');

	React.useEffect(() => {
		try {
			fetchProfile();
		} catch (error) {
			console.log(error);
		}
	}, []);

	const fetchProfile = async () => {
		console.log('USERNAME' + username);
		console.log(localStorage.getItem('authToken'));
		const result = await axios.get('/me', {
			headers: {
				Authorization: localStorage.getItem('authToken'),
			},
		});
		setUsername(result.data.username);
		setRole(result.data.role);
		setId(result.data.id);
		return result;
	};

	const handleModifyUsername = async (event) => {
		event.preventDefault();
		console.log(username);

		const json = {
			username: username,
		};
		const result = await axios.put(`/user/${id}`, json, {
			headers: {
				Authorization: localStorage.getItem('authToken'),
			},
		});
		setUsername(result.data.username);
	};

	const handleLogout = () => {
		localStorage.clear();
		navigate('/');
		window.location.reload(false);
	};

	return (
		<div class='flex flex-col grid place-items-center h-screen'>
			<form onSubmit={handleModifyUsername}>
				<label htmlFor="username">Username</label>
				<input
					type="text"
					id="username"
					name="username"
					value={username}
					onChange={(event) => setUsername(event.target.value)}
					required
				/>
				<input type="submit" value="Modify" />
			</form>

			<p>Role : {role}</p>
			<div id="logoutButton">
				<button onClick={handleLogout}>Logout</button>
			</div>
		</div>
	);
};

export default Profile;
