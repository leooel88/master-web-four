import axios from 'axios';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const BrickEdition = () => {
	const navigate = useNavigate();

	// const addCreateBrickButton = () => {
	// 	console.log('local storage : ' + localStorage.getItem('isAdmin'));
	// 	if (
	// 		localStorage.getItem('isAdmin') &&
	// 		localStorage.getItem('isAdmin') == true
	// 	) {
	// 		console.log('User is admin : showing button');
	// 		return (
	// 			<div className="createBrickButtonWrapper">
	// 				<button
	// 					className="createBrickButton"
	// 					onClick={handleCreateBrick}
	// 				>
	// 					Create new Brick
	// 				</button>
	// 			</div>
	// 		);
	// 	} else {
	// 		console.log('User is not admin : not showing button');
	// 		return;
	// 	}
	// };

	const createBrick = async (event) => {
		event.preventDefault();

		const json = {
			brick_name: event.target.brickName.value,
			brick_dim_h: event.target.brickDimH.value,
			brick_dim_l: event.target.brickDimL.value,
			brick_dim_w: event.target.brickDimW.value,
			brick_price: event.target.brickPrice.value,
			brick_quantity: event.target.brickQuantity.value,
			brick_image_url: event.target.brickImageUrl.value,
		};

		let result = '';
		try {
			result = await axios.post('/brick', json, {
				// headers: {
				// 	Authorization: localStorage.getItem('authToken'),
				// },
			});
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		navigate('/brickCatalog');
	};

	const handleCreateBrick = async (event) => {
		event.preventDefault();

		let result = '';
		try {
			result = await axios.get('/user/isadmin', {
				headers: {
					Authorization: localStorage.getItem('authToken'),
				},
			});
		} catch (error) {
			console.log(error);
		}

		console.log('Result data : ' + result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			await createBrick(event);
		} else {
			console.log('USER IS NOT ADMIN');
			navigate('/noRight');
		}
		return;
	};

	const deleteBrick = async (event) => {
		event.preventDefault();

		let result = '';
		try {
			result = await axios.delete(
				`/brick/${event.target.brickId.value}`,
				{
					// headers: {
					// 	Authorization: localStorage.getItem('authToken'),
					// },
				}
			);
		} catch (error) {
			console.log(error);
		}

		console.log(result.data);

		navigate('/brickCatalog');
	};

	const handleDeleteBrick = async (event) => {
		event.preventDefault();

		let result = '';
		try {
			result = await axios.get('/user/isadmin', {
				headers: {
					Authorization: localStorage.getItem('authToken'),
				},
			});
		} catch (error) {
			console.log(error);
		}

		console.log('Result data : ' + result.data);

		if (
			result &&
			result.data &&
			result.data.data &&
			result.data.data.admin &&
			result.data.data.admin == true
		) {
			console.log('USER IS ADMIN');
			await deleteBrick(event);
		} else {
			console.log('USER IS NOT ADMIN');
			navigate('/noRight');
		}
		return;
	};

	return (
		<div className="brickEditionWrapper">
			<div className="brickCreationFormWrapper">
				<div className="filterFormWrapper">
					<div className="brickCreationFormTitleWrapper">
						<p className="seqTitle">Create brick</p>
					</div>
					<form onSubmit={handleCreateBrick}>
						<label htmlFor="brickName">Brick name</label>
						<input
							type="text"
							id="brickName"
							name="brickName"
							required
						/>

						<label htmlFor="brickDimH">Brick dimension W</label>
						<input
							type="number"
							id="brickDimH"
							name="brickDimH"
							min="1"
							max="100"
							required
						/>

						<label htmlFor="brickDimL">Brick dimension L</label>
						<input
							type="number"
							id="brickDimL"
							name="brickDimL"
							min="1"
							max="100"
							required
						/>

						<label htmlFor="brickDimW">Brick dimension W</label>
						<input
							type="number"
							id="brickDimW"
							name="brickDimW"
							min="1"
							max="100"
							required
						/>

						<label htmlFor="brickPrice">Brick price</label>
						<input
							type="number"
							id="brickPrice"
							name="brickPrice"
							min="1"
							max="20000"
							required
						/>

						<label htmlFor="brickQuantity">Brick quantity</label>
						<input
							type="number"
							id="brickQuantity"
							name="brickQuantity"
							min="1"
							max="500000"
							required
						/>

						<label htmlFor="brickImageUrl">Brick image URL</label>
						<input
							type="text"
							name="brickImageUrl"
							id="brickImageUrl"
							maxLength="512"
							required
						/>
						<input type="submit" value="Create new Brick" />
					</form>
				</div>
			</div>
			<div className="brickDeleteFormWrapper">
				<div className="brickDeleteFormTitleWrapper">
					<p className="seqTitle">Delete brick</p>
				</div>
				<form onSubmit={handleDeleteBrick}>
					<label htmlFor="brickId">Brick id</label>
					<input type="number" id="brickId" name="brickId" required />

					<input type="submit" value="Delete Brick" />
				</form>
			</div>
		</div>
	);
};

export default BrickEdition;
