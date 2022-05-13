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
		localStorage.clear();
		navigate('/');
		window.location.reload(false);
	};

	const handleLogout = () => {
		localStorage.clear();
		navigate('/');
		window.location.reload(false);
	};

	return (
		<div class="flex flex-col grid place-items-center h-screen">
			<form
				onSubmit={handleModifyUsername}
				class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
			>
				<div class="mb-4">
					<label
						htmlFor="username"
						class="block text-gray-700 text-sm font-bold mb-2"
					>
						Username
					</label>
					<input
						class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						type="text"
						id="username"
						name="username"
						value={username}
						onChange={(event) => setUsername(event.target.value)}
						required
					/>
				</div>
				<div class="flex items-center justify-between">
					<input
						type="submit"
						value="Modify"
						class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
					/>
				</div>
			</form>

			<p class="font-medium leading-tight text-5xl mt-0 mb-2 text-black-600">
				Role : {role}
			</p>
			<div id="logoutButton">
				<button
					onClick={handleLogout}
					class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
				>
					Logout
				</button>
			</div>
		</div>
	);
};

export default Profile;
