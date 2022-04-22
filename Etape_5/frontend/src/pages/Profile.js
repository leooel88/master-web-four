import axios from 'axios';
import React from 'react';

const Profile = () => {
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
		console.log(localStorage.getItem('authToken'));
		const result = await axios.get('/me', {
			headers: {
				'Authorization': localStorage.getItem('authToken'),
			},
		});
		setUsername(result.data.username);
		setRole(result.data.role);
		setId(result.data.id);
		return result;
	};

	const handleModifyUsername = async (event) => {
		const json = {
			username: event.username.value,
		};
		const result = await axios.update(`/user/${id}`, json);
		setUsername(result.data.username);
	};

	return (
		<>
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
		</>
	);
};

export default Profile;
